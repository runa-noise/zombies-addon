package kr.hahaha98757.zombiesaddon.config.categories

import net.minecraftforge.common.config.Configuration

class Patcher: AbstractCategory() {
    override val categoryName = "Patcher"
    override val categoryKey = "patcher"

    var fixRifleSound = true
    var koreanPatch = false
    var zombiesOverlayInKo = false

    override fun sync(config: Configuration) {
        fixRifleSound = addOption(config.get(categoryName, "fixRifleSound", true)).boolean
        koreanPatch = addOption(config.get(categoryName, "koreanPatch", false)).boolean
        zombiesOverlayInKo = addOption(config.get(categoryName, "zombiesOverlayInKo", false)).boolean
    }
}