package kr.runanoise.zombiesaddon

import kr.runanoise.zombiesaddon.config.ZAConfig
import kr.runanoise.zombiesaddon.enums.Difficulty
import kr.runanoise.zombiesaddon.events.LastClientTickEvent
import kr.runanoise.zombiesaddon.game.GameManager
import kr.runanoise.zombiesaddon.modules.AutoRejoin
import kr.runanoise.zombiesaddon.modules.BlockAlarm
import kr.runanoise.zombiesaddon.modules.PlayerVisibility
import kr.runanoise.zombiesaddon.utils.*
import net.minecraftforge.client.event.ClientChatReceivedEvent
import net.minecraftforge.client.event.RenderGameOverlayEvent
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.event.entity.EntityJoinWorldEvent
import net.minecraftforge.fml.common.Loader
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

object System {

    fun registerAll() {
        MinecraftForge.EVENT_BUS.register(this)
        MinecraftForge.EVENT_BUS.register(ThePlayerJoinHandler)
    }

    @SubscribeEvent
    fun onRender(@Suppress("unused") event: RenderGameOverlayEvent.Text) {
        var i = 1

        val color = when {
            ZombiesAddon.instance.debug -> 0x55ff55
            ZAConfig.general.speedrunMode -> 0x55ffff
            else -> 0xffff55
        }
        var str = "$NAME v$VERSION"
        fr.drawStringWithShadow(str, HudUtils.getModNameStrX(str), HudUtils.getModNameStrY(), color)

        str = "Player Visibility: ${if (PlayerVisibility.isEnable()) "§aon" else "§coff"}"
        if (ZAConfig.pv.displayText) fr.drawStringWithShadow(str, HudUtils.getToggleTextStrX(str), HudUtils.getToggleTextStrY(i++), 0xffff55)

        str = "Block Alarm: ${if (BlockAlarm.isEnable()) "§aon" else "§coff"}"
        if (ZAConfig.blockAlarm.displayText) fr.drawStringWithShadow(str, HudUtils.getToggleTextStrX(str), HudUtils.getToggleTextStrY(i++), 0xffff55)

        str = "Auto Rejoin: ${if (AutoRejoin.isEnable()) "§aon" else "§coff"}"
        if (ZAConfig.autoRejoin.displayText) fr.drawStringWithShadow(str, HudUtils.getToggleTextStrX(str), HudUtils.getToggleTextStrY(i), 0xffff55)
    }

    @SubscribeEvent
    fun onChat(event: ClientChatReceivedEvent) {
        val message = event.message.unformattedText
        if (">" in message) return

        if ("The Helicopter is on its way! Hold out for 120 more seconds!" in message) GameManager.game?.helicopter()
        if (!isPractice()) return
        if ("Hard Difficulty" in message) GameManager.setDifficulty(Difficulty.HARD)
        else if ("RIP Difficulty" in message) GameManager.setDifficulty(Difficulty.RIP)
    }

    @SubscribeEvent
    fun onLastTick(@Suppress("unused") event: LastClientTickEvent) {
        Scoreboard.refresh()
        if (!ZombiesAddon.instance.debug) GameManager.removeGame()
    }
}

object ThePlayerJoinHandler {
    @SubscribeEvent
    fun onPlayerJoin(event: EntityJoinWorldEvent) {
        if (event.entity != mc.thePlayer) return
        MinecraftForge.EVENT_BUS.unregister(this)

        val za = ZombiesAddon.instance

        if (za.hasSST) {
            val sstVer = runCatching { Loader.instance().indexedModList["showspawntime"]!!.metadata.version }.getOrNull() ?: "Unknown"
            if (sstVer != SST_VERSION) addTranslatedChatLine("zombiesaddon.messages.sstDiffVer", sstVer, SST_VERSION)
        }
        if (za.hasZombiesUtils) {
            val zuVer = runCatching { Loader.instance().indexedModList["zombiesutils"]!!.metadata.version }.getOrNull() ?: "Unknown"
            if (zuVer != ZU_VERSION) addTranslatedChatLine("zombiesaddon.messages.zuDiffVer", zuVer, ZU_VERSION)
        }

        if (za.hasFeather) addTranslatedChatLine("zombiesaddon.messages.featherDetected")

        addChatLine("Most configuration settings have been reset with the §a4.9.0-alpha1§r update.")
    }
}