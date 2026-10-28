package kr.runanoise.zombiesaddon.game

import kr.runanoise.zombiesaddon.events.RoundStartEvent
import kr.runanoise.zombiesaddon.utils.*
import net.minecraft.entity.monster.EntityZombie
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.event.entity.EntityJoinWorldEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

class TimerCorrector(private val game: Game) {
    @SubscribeEvent
    fun onWaveSpawn(event: EntityJoinWorldEvent) {
        if (event.entity == mc.thePlayer) {
            logger.info("게임 ${game.serverNumber}의 타이머 보정을 취소합니다. (플레이어가 보정 전에 게임을 떠남)")
            MinecraftForge.EVENT_BUS.unregister(this)
            return
        }
        if (event.entity !is EntityZombie) return
        if (Scoreboard.isNotZombies) return
        if (game.serverNumber == getServerNumber()) {
            val ticks = game.gameMode.rounds[0].waves[0].ticks
            game.timer.correctStartTick(ticks)
            game.recorder.isCorrected = true
            logger.debug("타이머를 $ticks 틱으로 보정했습니다.")
        }
        MinecraftForge.EVENT_BUS.unregister(this)
    }

    @SubscribeEvent
    fun onRoundStart(event: RoundStartEvent) {
        if (event.game != game) return
        if (isPractice()) {
            game.recorder.isCorrected = true
            MinecraftForge.EVENT_BUS.unregister(this)
            return
        }
        if (event.game.round != 1) {
            logger.info("게임 ${game.serverNumber}의 타이머 보정을 취소합니다. (라운드 1에서 시작되었으나, 보정 전에 다른 라운드로 넘어감)")
            MinecraftForge.EVENT_BUS.unregister(this)
        }
    }
}