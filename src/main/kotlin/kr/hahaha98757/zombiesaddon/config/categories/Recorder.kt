package kr.hahaha98757.zombiesaddon.config.categories

import net.minecraftforge.common.config.Configuration

class Recorder: AbstractCategory() {
    override val categoryName = "Recorder"
    override val categoryKey = "recorder"

    var enable = true
    var defaultCategory = "general"
    var pbNotice = true

    override fun sync(config: Configuration) {
        enable = addOption(config.get(categoryName, "enable", true)).boolean
        defaultCategory = addOption(config.get(categoryName, "defaultCategory", "general")).string
        pbNotice = addOption(config.get(categoryName, "pbNotice", true)).boolean
    }
}