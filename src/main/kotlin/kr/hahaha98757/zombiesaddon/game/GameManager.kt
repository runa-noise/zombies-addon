package kr.hahaha98757.zombiesaddon.game

import kr.hahaha98757.zombiesaddon.ZombiesAddon
import kr.hahaha98757.zombiesaddon.data.ServerNumber
import kr.hahaha98757.zombiesaddon.enums.Difficulty
import kr.hahaha98757.zombiesaddon.enums.ZombiesMap
import kr.hahaha98757.zombiesaddon.events.GameEndEvent
import kr.hahaha98757.zombiesaddon.events.GameRemoveEvent
import kr.hahaha98757.zombiesaddon.events.RoundStartEvent
import kr.hahaha98757.zombiesaddon.modules.AutoSplits
import kr.hahaha98757.zombiesaddon.utils.*
import net.minecraftforge.common.MinecraftForge

object GameManager {
    private val games = mutableMapOf<ServerNumber, Game>()
    private var queuedServerNumber: ServerNumber? = null
    private var queuedDifficulty: Difficulty? = null

    val game get() = games[getServerNumber()]

    /**
     * @throws IllegalStateException 서버 번호 또는 맵을 알 수 없을 때
     */
    fun splitOrNew(round: Int, byCommand: Boolean = false) {
        val serverNumber = getServerNumber() ?: throw IllegalStateException("알 수 없는 서버 번호(Unknown Server Number)")
        if (serverNumber in games.keys) {
            if (round == 0) newGame(serverNumber, doNotCorrectTimer = byCommand)
            else games[serverNumber]?.pass(round)
        } else newGame(serverNumber, round, byCommand)
    }

    private fun newGame(serverNumber: ServerNumber, round: Int = 0, doNotCorrectTimer: Boolean = false) {
        val game = Game(getMap()?.getNormalGameMode() ?: throw IllegalStateException("알 수 없는 맵(Unknown Map)"), serverNumber, if (round == 0) 1 else round, doNotCorrectTimer)
        games[serverNumber]?.let {
            it.remove()
            MinecraftForge.EVENT_BUS.post(GameRemoveEvent(it))
        }
        games[serverNumber] = game
        AutoSplits.pass()
        RepeatedTask(100) {
            val difficulty = getDifficulty() ?: return@RepeatedTask
            game.changeDifficulty(difficulty)
            RepeatedTask.stop()
        }
        MinecraftForge.EVENT_BUS.post(RoundStartEvent(game))
        if (!isPractice()) return
        if (serverNumber == queuedServerNumber) queuedDifficulty?.let { game.changeDifficulty(it) }
        queuedDifficulty = null
    }

    fun endGame(serverNumber: ServerNumber, isWin: Boolean) {
        val game = games[serverNumber] ?: return
        game.gameEnd = true
        game.timer.stop()
        game.isWin = isWin
        if (isWin) {
            when (game.gameMode.map) {
                ZombiesMap.DEAD_END, ZombiesMap.BAD_BLOOD, ZombiesMap.PRISON -> game.pass(30)
                ZombiesMap.ALIEN_ARCADIUM -> game.pass(105)
            }
        }
        if (isWin) AutoSplits.pass()
        else AutoSplits.lose()
        if (mc.isCallingFromMinecraftThread) MinecraftForge.EVENT_BUS.post(GameEndEvent(game, isWin))
        else mc.addScheduledTask { MinecraftForge.EVENT_BUS.post(GameEndEvent(game, isWin)) }
    }

    fun removeGame() {
        if (!ZombiesAddon.instance.debug && !isNotPlayZombies()) return
        val queuedEndedGames = games.values.filter { it.gameEnd }
        for (game in queuedEndedGames) {
            game.remove()
            games.remove(game.serverNumber)
            MinecraftForge.EVENT_BUS.post(GameRemoveEvent(game))
        }
    }

    fun setDifficulty(difficulty: Difficulty) {
        if (!isPractice()) return
        queuedServerNumber = getServerNumber()
        games[queuedServerNumber]?.changeDifficulty(difficulty) ?: run { queuedDifficulty = difficulty }
    }
}