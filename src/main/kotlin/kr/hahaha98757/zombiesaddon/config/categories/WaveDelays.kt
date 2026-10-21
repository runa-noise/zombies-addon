package kr.hahaha98757.zombiesaddon.config.categories

import kr.hahaha98757.zombiesaddon.enums.WDHighlightStyle
import kr.hahaha98757.zombiesaddon.enums.WDTextStyle
import net.minecraftforge.common.config.Configuration

class WaveDelays: AbstractCategory() {
    override val categoryName = "Wave Delays"
    override val categoryKey = "waveDelays"

    var enable = true
    var playSounds = intArrayOf(-40, -20, 0)
    var customPlaySound = false
    var prefix = true
    var bossColor = true
    var textStyle = WDTextStyle.ZOMBIES_ADDON
    var highlightStyle = WDHighlightStyle.ZOMBIES_ADDON
    var hidePassedWave = false
    var rlModeOffset = -28
    var wrathModeOffset = -40

    override fun sync(config: Configuration) {
        enable = addOption(config.get(categoryName, "enable", true)).boolean
        playSounds = addOption(config.get(categoryName, "playSounds", intArrayOf(-40, -20, 0))).intList
        customPlaySound = addOption(config.get(categoryName, "customPlaySound", false)).boolean
        prefix = addOption(config.get(categoryName, "prefix", true)).boolean
        bossColor = addOption(config.get(categoryName, "bossColor", true)).boolean
        textStyle = WDTextStyle.fromText(addOption(config.get(categoryName, "textStyle", WDTextStyle.ZOMBIES_ADDON.toString(), "", WDTextStyle.arrays)).string)
        highlightStyle = WDHighlightStyle.fromText(addOption(config.get(categoryName, "highlightStyle", WDHighlightStyle.ZOMBIES_ADDON.toString(), "", WDHighlightStyle.arrays)).string)
        hidePassedWave = addOption(config.get(categoryName, "hidePassedWave", false)).boolean
        rlModeOffset = addOption(config.get(categoryName, "rlModeOffset", -28)).int
        wrathModeOffset = addOption(config.get(categoryName, "wrathModeOffset", -40)).int
    }
}