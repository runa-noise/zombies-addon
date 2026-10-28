package kr.runanoise.zombiesaddon.modules

import kr.runanoise.zombiesaddon.config.ZAConfig
import kr.runanoise.zombiesaddon.enums.ITTextStyle
import kr.runanoise.zombiesaddon.game.GameManager
import kr.runanoise.zombiesaddon.utils.HudUtils
import kr.runanoise.zombiesaddon.utils.fr
import kr.runanoise.zombiesaddon.utils.isNotPlayZombies
import net.minecraftforge.client.event.RenderGameOverlayEvent

object InternalTimer: Module("Internal Timer") {
    override fun onRender(event: RenderGameOverlayEvent.Text) {
        if (isNotPlayZombies()) return

        val game = GameManager.game ?: return

        val ticks = game.timer.roundTick
        val minutesPart = ticks / 1200
        val secondsPart = (ticks % 1200) / 20
        val tenthSecondsPart = ((ticks % 1200) % 20) / 2
        val hundredthSecondsPart = ((ticks % 1200) % 20) * 5
        val timer = when (ZAConfig.internalTimer.textStyle) {
            ITTextStyle.ZOMBIES_ADDON -> String.format("%d:%02d.%d", minutesPart, secondsPart, tenthSecondsPart)
            ITTextStyle.DETAILS -> String.format("%d:%02d.%02d", minutesPart, secondsPart, hundredthSecondsPart)
            ITTextStyle.SST -> String.format("%02d:%02d", minutesPart, secondsPart)
        }
        fr.drawStringWithShadow(timer, HudUtils.getAutoSplitsStrX(timer), HudUtils.getAutoSplitsStrY(), 0xffffff)
    }

    override fun isEnable() = ZAConfig.internalTimer.enable
}