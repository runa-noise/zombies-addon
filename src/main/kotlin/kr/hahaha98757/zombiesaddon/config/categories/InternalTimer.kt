package kr.hahaha98757.zombiesaddon.config.categories

import kr.hahaha98757.zombiesaddon.enums.ITMode
import kr.hahaha98757.zombiesaddon.enums.ITTextStyle
import net.minecraftforge.common.config.Configuration

class InternalTimer: AbstractCategory() {
    override val categoryName = "Internal Timer"
    override val categoryKey = "internalTimer"

    var enable = true
    var textStyle = ITTextStyle.ZOMBIES_ADDON
    var mode = ITMode.SERVER

    override fun sync(config: Configuration) {
        enable = addOption(config.get(categoryName, "enable", true)).boolean
        textStyle = ITTextStyle.fromText(addOption(config.get(categoryName, "textStyle", ITTextStyle.ZOMBIES_ADDON.toString(), "", ITTextStyle.arrays)).string)
        mode = ITMode.fromText(addOption(config.get(categoryName, "mode", ITMode.SERVER.toString(), "", ITMode.arrays)).string)
    }
}