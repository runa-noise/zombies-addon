package kr.runanoise.zombiesaddon.utils

import kr.runanoise.zombiesaddon.events.LastClientTickEvent
import kr.runanoise.zombiesaddon.events.ServerTickEvent
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

class DelayedTask(private var tick: Int = 2, private val serverTick: Boolean = false, private val block: () -> Unit) {
    init {
        MinecraftForge.EVENT_BUS.register(this)
    }

    @Suppress("unused")
    @SubscribeEvent
    fun onClientTick(event: LastClientTickEvent) {
        if (serverTick) return
        if (tick <= 0) {
            MinecraftForge.EVENT_BUS.unregister(this)
            block()
        }
        tick--
    }

    @Suppress("unused")
    @SubscribeEvent
    fun onServerTick(event: ServerTickEvent) {
        if (!serverTick) return
        if (tick <= 0) {
            MinecraftForge.EVENT_BUS.unregister(this)
            block()
        }
        tick--
    }
}