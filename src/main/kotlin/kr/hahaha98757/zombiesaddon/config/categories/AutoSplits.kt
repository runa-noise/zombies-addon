package kr.hahaha98757.zombiesaddon.config.categories

import net.minecraftforge.common.config.Configuration

class AutoSplits: AbstractCategory() {
    override val categoryName = "Auto Splits"
    override val categoryKey = "autoSplits"

    var enable = false
    var host = "127.0.0.1"
    var port = 16834
    var passCommand = "startorsplit"
    var loseCommand = "pause"

    override fun sync(config: Configuration) {
        enable = addOption(config.get(categoryName, "enable", false)).boolean
        host = addOption(config.get(categoryName, "host", "127.0.0.1")).string
        port = addOption(config.get(categoryName, "port", 16834)).int
        passCommand = addOption(config.get(categoryName, "passCommand", "startorsplit")).string
        loseCommand = addOption(config.get(categoryName, "loseCommand", "pause")).string
    }
}