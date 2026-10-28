package kr.runanoise.zombiesaddon.events

import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.eventhandler.Event
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent

class LastClientTickEvent: Event() {
    object EventBridge {
        @SubscribeEvent
        fun onTick(event: TickEvent.ClientTickEvent) {
            if (event.phase == TickEvent.Phase.END) MinecraftForge.EVENT_BUS.post(LastClientTickEvent())
        }
    }
}