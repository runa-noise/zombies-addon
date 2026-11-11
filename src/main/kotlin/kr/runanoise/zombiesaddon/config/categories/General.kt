package kr.runanoise.zombiesaddon.config.categories

import kr.runanoise.zombiesaddon.enums.Language
import net.minecraftforge.common.config.Configuration

class General: AbstractCategory() {
    override val categoryName = "General"
    override val categoryKey = "general"

    var enableMod = true
    var language = Language.AUTO
    var speedrunMode = false
    var gameRemoveDelay = 60

    override fun sync(config: Configuration) {
        enableMod = addOption(config.get(categoryName, "enableMod", true)).boolean
        language = Language.fromText(addOption(config.get(categoryName, "language", Language.AUTO.toString(), "", Language.arrays)).string)
        speedrunMode = addOption(config.get(categoryName, "speedrunMode", false)).boolean
        gameRemoveDelay = addOption(config.get(categoryName, "gameRemoveDelay", 60, "", 0, 600)).int
    }
}