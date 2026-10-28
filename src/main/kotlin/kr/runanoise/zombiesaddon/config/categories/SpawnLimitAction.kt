package kr.runanoise.zombiesaddon.config.categories

import net.minecraftforge.common.config.Configuration

class SpawnLimitAction: AbstractCategory() {
    override val categoryName = "Spawn Limit Action"
    override val categoryKey = "sla"

    var autoSla = false
    var textColor = true
    var showActiveWindows = true
    var showInactiveWindows = false

    override fun sync(config: Configuration) {
        autoSla = addOption(config.get(categoryName, "autoSla", false)).boolean
        textColor = addOption(config.get(categoryName, "textColor", true)).boolean
        showActiveWindows = addOption(config.get(categoryName, "showActiveWindows", true)).boolean
        showInactiveWindows = addOption(config.get(categoryName, "showInactiveWindows", false)).boolean
    }
}