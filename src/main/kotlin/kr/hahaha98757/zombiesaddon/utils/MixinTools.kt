package kr.hahaha98757.zombiesaddon.utils

import kr.hahaha98757.zombiesaddon.events.ServerTickEvent
import kr.hahaha98757.zombiesaddon.game.GameManager
import net.minecraft.network.play.server.S03PacketTimeUpdate
import net.minecraft.network.play.server.S29PacketSoundEffect
import net.minecraft.network.play.server.S32PacketConfirmTransaction
import net.minecraft.network.play.server.S45PacketTitle
import net.minecraftforge.common.MinecraftForge

private var aaR10 = false
fun onSound(packet: S29PacketSoundEffect) {
    if (!mc.isCallingFromMinecraftThread) return
    if (Scoreboard.isNotZombies) return
    val sound = packet.soundName
    GameManager.runCatching {
        if (sound == "mob.wither.spawn") {
            aaR10 = false
            splitOrNew(Scoreboard.round)
        } else if (sound == "mob.guardian.curse" && !aaR10) {
            aaR10 = true
            splitOrNew(Scoreboard.round)
        }
    }.onFailure {
        logger.error("게임 또는 라운드 시작에 실패했습니다.", it)
        addTranslatedChat("zombiesaddon.game.failed.splitOrNew", it.message ?: "알 수 없음(Unknown)")
    }
}

fun onTitle(packet: S45PacketTitle) {
    if (!mc.isCallingFromMinecraftThread) return
    if (packet.type != S45PacketTitle.Type.TITLE) return
    if (Scoreboard.isNotZombies) return
    val title = packet.message.unformattedText.trim().withoutColor()
    val serverNumber = getServerNumber() ?: return
    val isWin = when (title) {
        in win -> true
        in lose -> false
        else -> return
    }
    GameManager.endGame(serverNumber, isWin)
}

fun onInventoryPacket(packet: S32PacketConfirmTransaction) {
    if (!mc.isCallingFromMinecraftThread) return
    if (packet.windowId == 0) MinecraftForge.EVENT_BUS.post(ServerTickEvent())
}

fun onTimeUpdate(packet: S03PacketTimeUpdate) {
    if (!mc.isCallingFromMinecraftThread) return
    GameManager.game?.timer?.onPacket(packet)
}