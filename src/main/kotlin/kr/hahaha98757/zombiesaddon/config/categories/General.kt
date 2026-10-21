package kr.hahaha98757.zombiesaddon.config.categories

import kr.hahaha98757.zombiesaddon.enums.Language
import net.minecraftforge.common.config.Configuration

class General: AbstractCategory() {
    override val categoryName = "general"
    override val categoryKey = "general"

    var enableMod = true
    var language = Language.AUTO
    var speedrunMode = false

    override fun sync(config: Configuration) {
        enableMod = addOption(config.get("general", "enableMod", true)).boolean
        language = Language.fromText(addOption(config.get("general", "language", Language.AUTO.toString(), "", Language.arrays)).string)
        speedrunMode = addOption(config.get("general", "speedrunMode", false)).boolean
    }
}