package kr.hahaha98757.zombiesaddon.game

import kr.hahaha98757.zombiesaddon.data.ServerNumber
import kr.hahaha98757.zombiesaddon.enums.Difficulty
import kr.hahaha98757.zombiesaddon.enums.GameMode
import kr.hahaha98757.zombiesaddon.enums.ZombiesMap
import kr.hahaha98757.zombiesaddon.events.RoundStartEvent
import kr.hahaha98757.zombiesaddon.modules.AutoSplits
import kr.hahaha98757.zombiesaddon.modules.recorder.Recorder
import kr.hahaha98757.zombiesaddon.utils.*
import net.minecraftforge.common.MinecraftForge

class Game(var gameMode: GameMode, val serverNumber: ServerNumber, var round: Int, doNotCorrectTimer: Boolean = false) {
    val timer = Timer(mc.theWorld).apply(MinecraftForge.EVENT_BUS::register)
    var gameEnd = false
    var isWin = false
        get() = if (gameEnd) field else throw IllegalStateException("게임이 아직 종료되지 않았습니다.")
    var helicopter = false
    val recorder = Recorder(this, doNotCorrectTimer)
    val timerCorrector = TimerCorrector(this)
    var lastRound = 0
    init {
        if (!doNotCorrectTimer) MinecraftForge.EVENT_BUS.register(timerCorrector)
    }

    val roundData get() = runCatching { gameMode.rounds[round - 1] }.getOrNull()

    fun changeDifficulty(difficulty: Difficulty) {
        gameMode = gameMode.appliedDifficulty(difficulty)
    }

    fun pass(round: Int) {
        if (round != 0) recorder.runCatching { record(isPractice()) }.onFailure {
            if (isPractice()) return
            logger.error("게임 기록을 실패했습니다.", it)
            addTranslatedChat("zombiesaddon.modules.recorder.failed", it.message ?: "알 수 없음(Unknown)")
        }
        if (gameEnd) return
        timer.split()
        AutoSplits.pass()
        if (!isPractice()) {
            this.round = round + 1
            MinecraftForge.EVENT_BUS.post(RoundStartEvent(this))
            return
        }
        DelayedTask(10, true) {
            val round = Scoreboard.round
            if (round == 0) return@DelayedTask
            this.round = round
            MinecraftForge.EVENT_BUS.post(RoundStartEvent(this))
        }
    }

    fun helicopter() {
        if (gameMode.map != ZombiesMap.PRISON) return
        helicopter = true
        lastRound = round
        pass(30)
    }

    fun escape() {
        if (gameMode.map != ZombiesMap.PRISON) return
        pass(31)
    }

    fun remove() {
        MinecraftForge.EVENT_BUS.unregister(timerCorrector)
        timer.unregister()
    }
}