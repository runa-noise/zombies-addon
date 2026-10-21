package kr.hahaha98757.zombiesaddon.modules

import kr.hahaha98757.zombiesaddon.config.ZAConfig
import kr.hahaha98757.zombiesaddon.enums.ITTextStyle
import kr.hahaha98757.zombiesaddon.game.GameManager
import kr.hahaha98757.zombiesaddon.utils.HudUtils
import kr.hahaha98757.zombiesaddon.utils.fr
import kr.hahaha98757.zombiesaddon.utils.isNotPlayZombies
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