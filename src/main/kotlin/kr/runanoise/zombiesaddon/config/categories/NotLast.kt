package kr.runanoise.zombiesaddon.config.categories

import net.minecraftforge.common.config.Configuration

class NotLast: AbstractCategory() {
    override val categoryName = "Not Last"
    override val categoryKey = "notLast"

    var enable = false
    var activationTimes = 100

    override fun sync(config: Configuration) {
        enable = addOption(config.get(categoryName, "enable", false)).boolean
        activationTimes = addOption(config.get(categoryName, "activationTimes", 100)).int
    }
}