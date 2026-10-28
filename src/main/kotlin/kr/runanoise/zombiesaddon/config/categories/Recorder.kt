package kr.runanoise.zombiesaddon.config.categories

import net.minecraftforge.common.config.Configuration

class Recorder: AbstractCategory() {
    override val categoryName = "Recorder"
    override val categoryKey = "recorder"

    var enable = true
    var defaultCategory = "general"
    var deltaFormatting = true
    var showGameTime = true
    var showSegment = true
    var compareRecord = true
    var newBestNotice = true
    var saveRecord = true

    override fun sync(config: Configuration) {
        enable = addOption(config.get(categoryName, "enable", true)).boolean
        defaultCategory = addOption(config.get(categoryName, "defaultCategory", "general")).string
        deltaFormatting = addOption(config.get(categoryName, "deltaFormatting", true)).boolean
        showGameTime = addOption(config.get(categoryName, "showGameTime", true)).boolean
        showSegment = addOption(config.get(categoryName, "showSegment", true)).boolean
        compareRecord = addOption(config.get(categoryName, "compareRecord", true)).boolean
        newBestNotice = addOption(config.get(categoryName, "newBestNotice", true)).boolean
        saveRecord = addOption(config.get(categoryName, "saveRecord", true)).boolean
    }
}