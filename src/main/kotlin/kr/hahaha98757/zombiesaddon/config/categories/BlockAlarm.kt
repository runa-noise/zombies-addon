package kr.hahaha98757.zombiesaddon.config.categories

import net.minecraftforge.common.config.Configuration

class BlockAlarm: AbstractCategory() {
    override val categoryName = "Block Alarm"
    override val categoryKey = "blockAlarm"

    var default = false
    var displayText = false

    override fun sync(config: Configuration) {
        default = addOption(config.get(categoryName, "default", false)).boolean
        displayText = addOption(config.get(categoryName, "displayText", false)).boolean
    }
}