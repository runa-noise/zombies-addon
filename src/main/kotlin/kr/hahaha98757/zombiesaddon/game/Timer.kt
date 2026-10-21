package kr.hahaha98757.zombiesaddon.game

import kr.hahaha98757.zombiesaddon.config.ZAConfig
import kr.hahaha98757.zombiesaddon.enums.Mode
import kr.hahaha98757.zombiesaddon.events.ServerTickEvent
import kr.hahaha98757.zombiesaddon.modules.WaveDelays
import kr.hahaha98757.zombiesaddon.utils.mc
import net.minecraft.network.play.server.S03PacketTimeUpdate
import net.minecraft.world.World
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent

class Timer(private val world: World) {
    private val serverTimer = ServerTimer()
    private val clientTimer = ClientTimer()
    var source = TimerSource.SERVER
        private set
    private var stop = false

    private val isClientMode get() = ZAConfig.internalTimer.mode == Mode.CLIENT

    val gameTick get() = if (!isClientMode && source == TimerSource.SERVER) serverTimer.gameTick
    else clientTimer.gameTick

    val roundTick get() = if (!isClientMode && source == TimerSource.SERVER) serverTimer.roundTick
    else clientTimer.roundTick

    fun split() {
        serverTimer.split()
        clientTimer.split()
    }

    fun stop() {
        stop = true
        serverTimer.stop = true
        clientTimer.stop = true
    }

    fun correctStartTick(tick: Int) {
        serverTimer.correctStartTick(tick)
        clientTimer.correctStartTick(tick)
    }

    fun onPacket(packet: S03PacketTimeUpdate) {
        if (isClientMode) return
        refreshSource()
        if (source == TimerSource.WAITING_PACKET) {
            val delta = packet.totalWorldTime - clientTimer.lastCorrectedTick
            serverTimer.serverTickCounter = serverTimer.lastCorrectedTick + delta
            source = TimerSource.SERVER
        }
        if (source == TimerSource.CLIENT) return
        clientTimer.lastCorrectedTick = packet.totalWorldTime
        serverTimer.lastCorrectedTick = serverTimer.currentTick
    }

    @SubscribeEvent
    fun onClientTick(event: TickEvent.ClientTickEvent) {
        if (event.phase != TickEvent.Phase.START) return
        if (isClientMode) return
        refreshSource()
    }

    private fun refreshSource() {
        if (stop) return
        if (world != mc.theWorld) {
            if (source != TimerSource.CLIENT) source = TimerSource.CLIENT
            return
        } else if (source == TimerSource.CLIENT) source = TimerSource.WAITING_PACKET
    }

    fun unregister() {
        MinecraftForge.EVENT_BUS.unregister(serverTimer)
        MinecraftForge.EVENT_BUS.unregister(this)
    }

    private inner class ServerTimer {
        var serverTickCounter = 0L

        @SubscribeEvent
        fun onServerTick(@Suppress("unused") event: ServerTickEvent) {
            serverTickCounter++
            if (isClientMode || source != TimerSource.SERVER) return
            WaveDelays.playSound()
        }

        init {
            MinecraftForge.EVENT_BUS.register(this)
        }
        private var startTick = currentTick
        private var roundStartTick = 0
        private var lastTick = 0L
        var lastCorrectedTick = 0L
        var stop = false
            set(value) {
                if (value) currentTick
                field = value
            }

        val gameTick get() = (currentTick - startTick).toInt()

        val roundTick get() = gameTick - roundStartTick

        val currentTick get() =
            if (!stop) serverTickCounter.also { lastTick = it } else lastTick

        fun split() {
            if (source != TimerSource.SERVER) {
                val delta = (clientTimer.currentTick - clientTimer.lastCorrectedTick).toInt()
                roundStartTick = (lastCorrectedTick + delta - startTick).toInt()
                return
            }
            roundStartTick = gameTick
        }

        fun correctStartTick(tick: Int) {
            startTick = currentTick - tick
        }
    }

    private class ClientTimer {
        private var startTick = currentTick
        private var roundStartTick = 0
        private var lastTick = 0L
        var lastCorrectedTick = 0L
        var stop = false
            set(value) {
                if (value) currentTick
                field = value
            }

        val gameTick get() = (currentTick - startTick).toInt()

        val roundTick get() = gameTick - roundStartTick

        val currentTick get() =
            if (mc.theWorld == null) 0
            else if (!stop) mc.theWorld.totalWorldTime.also { lastTick = it }
            else lastTick

        fun split() {
            roundStartTick = gameTick
        }

        fun correctStartTick(tick: Int) {
            startTick = currentTick - tick
        }
    }
}

enum class TimerSource {
    SERVER, CLIENT, WAITING_PACKET

}