package kr.hahaha98757.zombiesaddon.config.categories

import net.minecraftforge.common.config.Configuration

class LastWeapons: AbstractCategory() {
    override val categoryName = "Last Weapons"
    override val categoryKey = "lastWeapons"

    var enable = true
    var displayArmors = true
    var displayLevel = true
    var displayCooledDownSkill = true
    var workInGameOver = true

    override fun sync(config: Configuration) {
        enable = addOption(config.get(categoryName, "enable", true)).boolean
        displayArmors = addOption(config.get(categoryName, "displayArmors", true)).boolean
        displayLevel = addOption(config.get(categoryName, "displayLevel", true)).boolean
        displayCooledDownSkill = addOption(config.get(categoryName, "displayCooledDownSkill", true)).boolean
        workInGameOver = addOption(config.get(categoryName, "workInGameOver", true)).boolean
    }
}