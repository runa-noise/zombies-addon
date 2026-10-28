package kr.runanoise.zombiesaddon.utils

import kr.runanoise.zombiesaddon.events.LastClientTickEvent
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

class RepeatedTask(private var tick: Int = 100, private val endTask: () -> Unit = {}, private val task: () -> Unit) {
    init {
        MinecraftForge.EVENT_BUS.register(this)
    }

    @Suppress("unused")
    @SubscribeEvent
    fun onTick(event: LastClientTickEvent) {
        try {
            if (tick <= 0) {
                MinecraftForge.EVENT_BUS.unregister(this)
                endTask()
                return
            }
            task()
            tick--
        } catch (e: StopRepeatedTask) {
            MinecraftForge.EVENT_BUS.unregister(this)
        }
    }

    companion object {
        fun stop(): Nothing = throw StopRepeatedTask()
    }
}

private class StopRepeatedTask: Exception()