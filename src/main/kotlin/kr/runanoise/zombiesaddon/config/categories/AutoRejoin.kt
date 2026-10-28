package kr.runanoise.zombiesaddon.config.categories

import net.minecraftforge.common.config.Configuration

class AutoRejoin: AbstractCategory() {
    override val categoryName = "Auto Rejoin"
    override val categoryKey = "autoRejoin"

    var default = false
    var displayText = false
    var durationTimes = 100

    override fun sync(config: Configuration) {
        default = addOption(config.get(categoryName, "default", false)).boolean
        displayText = addOption(config.get(categoryName, "displayText", false)).boolean
        durationTimes = addOption(config.get(categoryName, "durationTimes", 100)).int
    }
}