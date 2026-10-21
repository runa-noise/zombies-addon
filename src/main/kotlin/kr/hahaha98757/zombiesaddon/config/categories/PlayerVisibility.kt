package kr.hahaha98757.zombiesaddon.config.categories

import kr.hahaha98757.zombiesaddon.enums.SemiPvMode
import net.minecraftforge.common.config.Configuration
import net.minecraftforge.fml.client.config.GuiConfigEntries

class PlayerVisibility: AbstractCategory() {
    override var categoryName = "Player Visibility"
    override val categoryKey = "playerVisibility"

    var default = true
    var displayText = true
    var range = 2.5
    var enableSemiPv = true
    var fastSemiPv = false
    var semiPvMode = SemiPvMode.LINEAR
    var semiPvRange = 5.0
    var semiPvMinAlpha = 0.0
    var semiPvMaxAlpha = 1.0
        get() = if (field < semiPvMinAlpha) semiPvMinAlpha else field

    override fun sync(config: Configuration) {
        default = addOption(config.get(categoryName, "default", true)).boolean
        displayText = addOption(config.get(categoryName, "displayText", true)).boolean
        range = addOption(config.get(categoryName, "range", 2.5)).double
        enableSemiPv = addOption(config.get(categoryName, "enableSemiPv", true)).boolean
        fastSemiPv = addOption(config.get(categoryName, "fastSemiPv", false)).boolean
        semiPvMode = SemiPvMode.fromText(addOption(config.get(categoryName, "semiPvMode", SemiPvMode.LINEAR.toString(), "", SemiPvMode.arrays)).string)
        semiPvRange = addOption(config.get(categoryName, "semiPvRange", 5.0)).double
        semiPvMinAlpha = addOption(config.get(categoryName, "semiPvMinAlpha", 0.0, "", 0.0, 1.0).apply {
            configEntryClass = GuiConfigEntries.NumberSliderEntry::class.java
        }).double
        semiPvMaxAlpha = addOption(config.get(categoryName, "semiPvMaxAlpha", 1.0, "", 0.0, 1.0).apply {
            configEntryClass = GuiConfigEntries.NumberSliderEntry::class.java
        }).double
    }
}