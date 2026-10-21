package kr.hahaha98757.zombiesaddon.config.categories

import net.minecraftforge.common.config.Configuration

class Modules: AbstractCategory() {
    override val categoryName = "Modules"
    override val categoryKey = "modules"

    var enableNotLast = false
    var enablePowerupPatterns = true
    var enableBetterZombiesLeft = true
    var textMacro = "T"

    override fun sync(config: Configuration) {
        enableNotLast = addOption(config.get(categoryName, "enableNotLast", false)).boolean
        enablePowerupPatterns = addOption(config.get(categoryName, "enablePowerupPatterns", true)).boolean
        enableBetterZombiesLeft = addOption(config.get(categoryName, "enableBetterZombiesLeft", true)).boolean
        textMacro = addOption(config.get(categoryName, "textMacro", "T")).string
    }
}