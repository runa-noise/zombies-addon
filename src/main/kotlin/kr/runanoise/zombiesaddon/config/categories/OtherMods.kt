package kr.runanoise.zombiesaddon.config.categories

import net.minecraftforge.common.config.Configuration

class OtherMods: AbstractCategory() {
    override val categoryName = "Other Mods"
    override val categoryKey = "otherMods"

    var disableSpawnTimeOfSst = true
    var disableTimerOfZombiesUtils = true

    override fun sync(config: Configuration) {
        disableSpawnTimeOfSst = addOption(config.get(categoryName, "disableSpawnTimeOfSst", true)).boolean
        disableTimerOfZombiesUtils = addOption(config.get(categoryName, "disableTimerOfZombiesUtils", true)).boolean
    }
}