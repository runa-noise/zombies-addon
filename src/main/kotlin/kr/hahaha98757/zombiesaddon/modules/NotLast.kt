package kr.hahaha98757.zombiesaddon.modules

import kr.hahaha98757.zombiesaddon.ZombiesAddon
import kr.hahaha98757.zombiesaddon.config.ZAConfig
import kr.hahaha98757.zombiesaddon.enums.Status
import kr.hahaha98757.zombiesaddon.events.RoundStartEvent
import kr.hahaha98757.zombiesaddon.utils.*
import net.minecraft.scoreboard.ScoreObjective
import net.minecraft.scoreboard.Scoreboard

object NotLast: Module("Not Last") {
    private val scoreMap = mutableMapOf<String, Int>()
    private lateinit var scoreboard: Scoreboard
    private lateinit var scoreObjective: ScoreObjective
    private var debug = false
    val debugPlayerList = mutableListOf<String>()

    override fun onRoundStart(event: RoundStartEvent) {
        if (AutoRejoin.isEnable()) return
        if (event.game.round == 1) return
        if (isNotPlayZombies()) return
        var quitCounter = 0
        debug = ZombiesAddon.instance.debug
        if (!debug) {
            for (i in getPlayerStatus()) if (i == Status.QUIT) quitCounter++
            if (quitCounter == 3) return
        }

        if (!debug) {
            scoreboard = mc.theWorld.scoreboard
            scoreObjective = scoreboard.getObjectiveInDisplaySlot(0) ?: return
            for (score in scoreboard.getSortedScores(scoreObjective))
                scoreMap[score.playerName] = score.scorePoints
        }

        RepeatedTask(endTask = { addTranslatedChat("zombiesaddon.modules.notLast.failed") }) {
            val players: MutableList<String>
            if (!debug) {
                players = mutableListOf()
                for (score in scoreboard.getSortedScores(scoreObjective)) {
                    val kills = scoreMap[score.playerName] ?: continue
                    if (score.scorePoints != kills) players += score.playerName
                }
            } else {
                players = debugPlayerList.toMutableList()
                debugPlayerList.clear()
            }


            if (players.isNotEmpty()) {
                printLast(players)
                RepeatedTask.stop()
            }
        }
    }

    private fun printLast(players: List<String>): Unit = StringBuilder().run {
        append(players[0].withNameColor())

        if (players.size == 2) {
            append(" §e${getTranslatedString("zombiesaddon.modules.notLast.or")} ${players[1].withNameColor()}")
            addTranslatedChat("zombiesaddon.modules.notLast.printLast", toString())
            return
        }

        for (i in 1..players.lastIndex) if (i != players.lastIndex) append("§e${getComma()}${players[i].withNameColor()}")
        else append("§e${getComma()}${getTranslatedString("zombiesaddon.modules.notLast.or")} ${players[i].withNameColor()}")

        addTranslatedChat("zombiesaddon.modules.notLast.printLast", toString())
    }

    override fun isEnable() = ZAConfig.modules.enableNotLast

    private fun getComma() = getTranslatedString("zombiesaddon.modules.notLast.comma").let {
        if (it.endsWith("{space}")) "${it.removeSuffix("{space}")} "
        else it
    }
}