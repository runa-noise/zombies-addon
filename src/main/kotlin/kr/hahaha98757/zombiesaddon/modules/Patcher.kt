package kr.hahaha98757.zombiesaddon.modules

import kr.hahaha98757.zombiesaddon.config.ZAConfig
import kr.hahaha98757.zombiesaddon.utils.addChat
import kr.hahaha98757.zombiesaddon.utils.isDisable
import kr.hahaha98757.zombiesaddon.utils.isNotPlayZombies
import net.minecraft.network.play.server.S29PacketSoundEffect
import net.minecraftforge.client.event.ClientChatReceivedEvent

object Patcher: AlwaysEnableModule("Patcher") {
    override fun onChat(event: ClientChatReceivedEvent) {
        val message = event.message.unformattedText
        if ("<" in message) return
        ingame(message, event)
        zombiesOverlay(message)
    }

    private fun ingame(message: String, event: ClientChatReceivedEvent) {
        if (!ZAConfig.patcher.koreanPatch) return
        if (isNotPlayZombies()) return

        when (message) {
            "This weapon is out of ammo! You can refill ammo at the place that you purchased the weapon or through collecting the Max Ammo Power Up." ->
                addChat("§c§l무기는 탄약이 없습니다! 무기를 구입한 곳에서 탄약을 채우거나§r§9§l 탄약 충전§c§l 파워업을 획득하세요.")
            "이 행운의 상자는 지금 사용할 수 없습니다! 묘지에서 다른 활성화된 행운의 상자를 찾으세요!" ->
                addChat("§c이 행운의 상자는 지금 사용할 수 없습니다! 지하실에서 다른 활성화된 행운의 상자를 찾으세요!")
            else -> return
        }
        event.isCanceled = true
    }

    private fun zombiesOverlay(message: String) {
        if (!ZAConfig.patcher.zombiesOverlayInKo) return

        if (message.startsWith("온라인: ")) addChat("ONLINE: ${message.split(":")[1].trim()}")
        if ("님이 참여했습니다!" in message) {
            val name = message.split(" ")[0]
            val number = message.split(" ")[3].split("/")[0].replace(Regex("[^0-9]"), "")
            addChat("$name has joined ($number/4)!")
        }
        if ("님이 나갔습니다!" in message) {
            val name = message.split(" ")[0]
            addChat("$name has quit!")
        }
        if ("서버로 이동합니다" in message) {
            val text = message.split(" ")[0]
            addChat("Sending you to $text!")
        }
    }

    fun fixRifleSound(packet: S29PacketSoundEffect): S29PacketSoundEffect {
        if (isDisable()) return packet
        if (isNotPlayZombies()) return packet
        if (!ZAConfig.patcher.fixRifleSound) return packet
        return if (packet.soundName == "fireworks.largeblast")
            S29PacketSoundEffect("fireworks.largeBlast", packet.x, packet.y, packet.z, packet.volume, packet.pitch)
        else packet
    }

}