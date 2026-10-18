package kr.hahaha98757.zombiesaddon.config

import com.seosean.showspawntime.ShowSpawnTime
import com.seosean.showspawntime.config.MainConfiguration
import kr.hahaha98757.zombiesaddon.MODID
import kr.hahaha98757.zombiesaddon.ZombiesAddon
import kr.hahaha98757.zombiesaddon.enums.*
import kr.hahaha98757.zombiesaddon.utils.HudUtils
import net.minecraftforge.common.config.ConfigElement
import net.minecraftforge.common.config.Configuration
import net.minecraftforge.common.config.Property
import net.minecraftforge.fml.client.config.DummyConfigElement.DummyCategoryElement
import net.minecraftforge.fml.client.config.IConfigElement
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

object ZAConfig {
    lateinit var config: Configuration

    private val categoryGeneral = Category("General")
    private val categoryModules = Category("Modules")
    private val categoryPv = Category("Player Visibility")
    private val categoryBlockAlarm = Category("Block Alarm")
    private val categoryAutoSplits = Category("Auto Splits")
    private val categoryInternalTimer = Category("Internal Timer")
    private val categoryWaveDelays = Category("Wave Delays")
    private val categorySla = Category("SLA")
    private val categoryAutoRejoin = Category("Auto Rejoin")
    private val categoryLastWeapons = Category("Last Weapons")
    private val categoryRecorder = Category("Recorder")
    private val categoryPatcher = Category("Patcher")
    private val categoryOtherMods = Category("Other Mods")

    var enableMod = true
    var language = Language.AUTO
    var speedrunMode = false

    var toggleNotLast = false
    var togglePowerupPatterns = true
    var toggleBetterZombiesLeft = true
    var textMacro = "T"

    var pvDefault = true
    var pvText = true
    var pvRange = 2.5
    var pvToggleSemiPv = true
    var pvSemiPvRange = 5.0
    var pvSemiPvMinAlpha = 0.0
    var pvSemiPvMaxAlpha = 1.0
        get() = if (field < pvSemiPvMinAlpha) pvSemiPvMinAlpha else field
    var pvSemiPvMode = SemiPvMode.LINEAR
    var pvFastSemiPv = false

    var blockAlarmDefault = false
    var blockAlarmText = false

    var autoSplitsToggle = false
    var autoSplitsHost = "127.0.0.1"
    var autoSplitsPort = 16384

    var internalTimerToggle = true
    var internalTimerTextStyle = ITTextStyle.ZOMBIES_ADDON
    var internalTimerMode = Mode.SERVER

    var waveDelaysToggle = true
    var waveDelaysPlaySounds = intArrayOf(-40, -20, 0)
    var waveDelaysCustomPlaySound = false
    var waveDelaysPrefix = true
    var waveDelaysBossColor = true
    var waveDelaysTextStyle = WDTextStyle.ZOMBIES_ADDON
    var waveDelaysHighlightStyle = HighlightStyle.ZOMBIES_ADDON
    var waveDelaysHidePassedWave = false
    var waveDelaysRlModeOffset = -28
    var waveDelaysWrathModeOffset = -40
    
    var slaAutoSla = false
    var slaTextColor = true
    var slaActivatedWindows = true
    var slaUnactivatedWindows = false

    var autoRejoinDefault = false
    var autoRejoinText = false

    var lwToggle = true
    var lwDisplayArmors = true
    var lwDisplayWeaponsLevel = true
    var lwDisplayCooledDownSkill = true
    var lwWorkInGameOver = true

    var recorderToggle = true
    var recorderDefaultCategory = "general"
        get() = if ("/" in field || "\\" in field) "general" else field
    var recorderPbNotice = true

    var patcherFixRifleSound = true
    var patcherKoreanPatch = false
    var patcherZombiesOverlayInKo = true

    var disableSpawnTimeOfSst = true
    var disableTimerOfZombiesUtils = true

    fun init(config: Configuration) {
        this.config = config
        config.load()
        sync()
    }

    private fun sync() {
        // 일반
        val enableModKey = "zombiesaddon.config.enableMod"
        enableMod = addOption(categoryGeneral, enableModKey, config.get(
            categoryGeneral.name,
            "enableMod",
            true,
            "$enableModKey.description"
        )).boolean

        val languageKey = "zombiesaddon.config.language"
        language = Language.fromText(addOption(categoryGeneral, languageKey, config.get(
            categoryGeneral.name,
            "language",
            Language.AUTO.toString(),
            "$languageKey.description",
            Language.arrays
        )).string)

        val speedrunModeKey = "zombiesaddon.config.speedrunMode"
        speedrunMode = addOption(categoryGeneral, speedrunModeKey, config.get(
            categoryGeneral.name,
            "speedrunMode",
            false,
            "$speedrunModeKey.description"
        )).boolean


        // 모듈
        val toggleNotLastKey = "zombiesaddon.config.toggleNotLast"
        toggleNotLast = addOption(categoryModules, toggleNotLastKey, config.get(
            categoryModules.name,
            "toggleNotLast",
            false,
            "$toggleNotLastKey.description"
        )).boolean

        val togglePowerupPatternsKey = "zombiesaddon.config.togglePowerupPatterns"
        togglePowerupPatterns = addOption(categoryModules, togglePowerupPatternsKey, config.get(
            categoryModules.name,
            "togglePowerupPatterns",
            true,
            "$togglePowerupPatternsKey.description"
        )).boolean

        val toggleBetterZombiesLeftKey = "zombiesaddon.config.toggleBetterZombiesLeft"
        toggleBetterZombiesLeft = addOption(categoryModules, toggleBetterZombiesLeftKey, config.get(
            categoryModules.name,
            "toggleBetterZombiesLeft",
            true,
            "$toggleBetterZombiesLeftKey.description"
        )).boolean

        val textMacroKey = "zombiesaddon.config.textMacro"
        textMacro = addOption(categoryModules, textMacroKey, config.get(
            categoryModules.name,
            "textMacro",
            "T",
            "$textMacroKey.description"
        )).string


        // Player Visibility
        val pvDefaultKey = "zombiesaddon.config.pvDefault"
        pvDefault = addOption(categoryPv, pvDefaultKey, config.get(
            categoryPv.name,
            "pvDefault",
            true,
            "$pvDefaultKey.description"
        )).boolean

        val pvTextKey = "zombiesaddon.config.pvText"
        pvText = addOption(categoryPv, pvTextKey, config.get(
            categoryPv.name,
            "pvText",
            true,
            "$pvTextKey.description"
        )).boolean

        val pvRangeKey = "zombiesaddon.config.pvRange"
        pvRange = addOption(categoryPv, pvRangeKey, config.get(
            categoryPv.name,
            "pvRange",
            2.5,
            "$pvRangeKey.description",
            0.0, 10.0
        )).double

        val pvToggleSemiPvKey = "zombiesaddon.config.pvToggleSemiPv"
        pvToggleSemiPv = addOption(categoryPv, pvToggleSemiPvKey, config.get(
            categoryPv.name,
            "pvToggleSemiPv",
            true,
            "$pvToggleSemiPvKey.description"
        )).boolean

        val pvSemiPvRangeKey = "zombiesaddon.config.pvSemiPvRange"
        pvSemiPvRange = addOption(categoryPv, pvSemiPvRangeKey, config.get(
            categoryPv.name,
            "pvSemiPvRange",
            5.0,
            "$pvSemiPvRangeKey.description",
            0.0, 10.0
        )).double

        val pvSemiPvMinAlphaKey = "zombiesaddon.config.pvSemiPvMinAlpha"
        pvSemiPvMinAlpha = addOption(categoryPv, pvSemiPvMinAlphaKey, config.get(
            categoryPv.name,
            "pvSemiPvMinAlpha",
            0.0,
            "$pvSemiPvMinAlphaKey.description",
            0.0, 1.0
        )).double

        val pvSemiPvMaxAlphaKey = "zombiesaddon.config.pvSemiPvMaxAlpha"
        pvSemiPvMaxAlpha = addOption(categoryPv, pvSemiPvMaxAlphaKey, config.get(
            categoryPv.name,
            "pvSemiPvMaxAlpha",
            1.0,
            "$pvSemiPvMaxAlphaKey.description",
            0.0, 1.0
        )).double

        val pvSemiPvModeKey = "zombiesaddon.config.pvSemiPvMode"
        pvSemiPvMode = SemiPvMode.fromText(addOption(categoryPv, pvSemiPvModeKey, config.get(
            categoryPv.name,
            "pvSemiPvMode",
            SemiPvMode.LINEAR.toString(),
            "$pvSemiPvModeKey.description",
            SemiPvMode.arrays
        )).string)

        val pvFastSemiPvKey = "zombiesaddon.config.pvFastSemiPv"
        pvFastSemiPv = addOption(categoryPv, pvFastSemiPvKey, config.get(
            categoryPv.name,
            "pvFastSemiPv",
            false,
            "$pvFastSemiPvKey.description"
        )).boolean


        // Block Alarm
        val blockAlarmDefaultKey = "zombiesaddon.config.blockAlarmDefault"
        blockAlarmDefault = addOption(categoryBlockAlarm, blockAlarmDefaultKey, config.get(
            categoryBlockAlarm.name,
            "blockAlarmDefault",
            false,
            "$blockAlarmDefaultKey.description"
        )).boolean

        val blockAlarmTextKey = "zombiesaddon.config.blockAlarmText"
        blockAlarmText = addOption(categoryBlockAlarm, blockAlarmTextKey, config.get(
            categoryBlockAlarm.name,
            "blockAlarmText",
            false,
            "$blockAlarmTextKey.description"
        )).boolean


        // Auto Splits
        val autoSplitsToggleKey = "zombiesaddon.config.autoSplitsToggle"
        autoSplitsToggle = addOption(categoryAutoSplits, autoSplitsToggleKey, config.get(
            categoryAutoSplits.name,
            "autoSplitsToggle",
            false,
            "$autoSplitsToggleKey.description"
        )).boolean

        val autoSplitsHostKey = "zombiesaddon.config.autoSplitsHost"
        autoSplitsHost = addOption(categoryAutoSplits, autoSplitsHostKey, config.get(
            categoryAutoSplits.name,
            "autoSplitsHost",
            "127.0.0.1",
            "$autoSplitsHostKey.description"
        )).string

        val autoSplitsPortKey = "zombiesaddon.config.autoSplitsPort"
        autoSplitsPort = addOption(categoryAutoSplits, autoSplitsPortKey, config.get(
            categoryAutoSplits.name,
            "autoSplitsPort",
            16834,
            "$autoSplitsPortKey.description",
            0, 65535
        )).int


        // Internal Timer
        val internalTimerToggleKey = "zombiesaddon.config.internalTimerToggle"
        internalTimerToggle = addOption(categoryInternalTimer, internalTimerToggleKey, config.get(
            categoryInternalTimer.name,
            "internalTimerToggle",
            true,
            "$internalTimerToggleKey.description"
        )).boolean

        val internalTimerTextStyleKey = "zombiesaddon.config.internalTimerTextStyle"
        internalTimerTextStyle = ITTextStyle.fromText(addOption(categoryInternalTimer, internalTimerTextStyleKey, config.get(
            categoryInternalTimer.name,
            "internalTimerTextStyle",
            ITTextStyle.ZOMBIES_ADDON.toString(),
            "$internalTimerTextStyleKey.description",
            ITTextStyle.arrays
        )).string)

        val internalTimerModeKey = "zombiesaddon.config.internalTimerMode"
        internalTimerMode = Mode.fromText(addOption(categoryInternalTimer, internalTimerModeKey, config.get(
            categoryInternalTimer.name,
            "internalTimerMode",
            Mode.SERVER.toString(),
            "$internalTimerModeKey.description",
            Mode.arrays
        )).string)


        // Wave Delays
        val waveDelaysToggleKey = "zombiesaddon.config.waveDelaysToggle"
        waveDelaysToggle = addOption(categoryWaveDelays, waveDelaysToggleKey, config.get(
            categoryWaveDelays.name,
            "waveDelaysToggle",
            true,
            "$waveDelaysToggleKey.description"
        )).boolean

        val waveDelaysPlaySoundsKey = "zombiesaddon.config.waveDelaysPlaySounds"
        waveDelaysPlaySounds = addOption(categoryWaveDelays, waveDelaysPlaySoundsKey, config.get(
            categoryWaveDelays.name,
            "waveDelaysPlaySounds",
            intArrayOf(-40, -20, 0),
            "$waveDelaysPlaySoundsKey.description",
            -200, 200, false, 5
        )).intList

        val waveDelaysCustomPlaySoundKey = "zombiesaddon.config.waveDelaysCustomPlaySound"
        waveDelaysCustomPlaySound = addOption(categoryWaveDelays, waveDelaysCustomPlaySoundKey, config.get(
            categoryWaveDelays.name,
            "waveDelaysCustomPlaySound",
            false,
            "$waveDelaysCustomPlaySoundKey.description"
        )).boolean

        val waveDelaysPrefixKey = "zombiesaddon.config.waveDelaysPrefix"
        waveDelaysPrefix = addOption(categoryWaveDelays, waveDelaysPrefixKey, config.get(
            categoryWaveDelays.name,
            "waveDelaysPrefix",
            true,
            "$waveDelaysPrefixKey.description"
        )).boolean

        val waveDelaysBossColorKey = "zombiesaddon.config.waveDelaysBossColor"
        waveDelaysBossColor = addOption(categoryWaveDelays, waveDelaysBossColorKey, config.get(
            categoryWaveDelays.name,
            "waveDelaysBossColor",
            true,
            "$waveDelaysBossColorKey.description"
        )).boolean

        val waveDelaysTextStyleKey = "zombiesaddon.config.waveDelaysTextStyle"
        waveDelaysTextStyle = WDTextStyle.fromText(addOption(categoryWaveDelays, waveDelaysTextStyleKey, config.get(
            categoryWaveDelays.name,
            "waveDelaysTextStyle",
            WDTextStyle.ZOMBIES_ADDON.toString(),
            "$waveDelaysTextStyleKey.description",
            WDTextStyle.arrays
        )).string)

        val waveDelaysHighlightStyleKey = "zombiesaddon.config.waveDelaysHighlightStyle"
        waveDelaysHighlightStyle = HighlightStyle.fromText(addOption(categoryWaveDelays, waveDelaysHighlightStyleKey, config.get(
            categoryWaveDelays.name,
            "waveDelaysHighlightStyle",
            HighlightStyle.ZOMBIES_ADDON.toString(),
            "$waveDelaysHighlightStyleKey.description",
            HighlightStyle.arrays
        )).string)

        val waveDelaysHidePassedWaveKey = "zombiesaddon.config.waveDelaysHidePassedWave"
        waveDelaysHidePassedWave = addOption(categoryWaveDelays, waveDelaysHidePassedWaveKey, config.get(
            categoryWaveDelays.name,
            "waveDelaysHidePassedWave",
            false,
            "$waveDelaysHidePassedWaveKey.description"
        )).boolean

        val waveDelaysRlModeOffsetKey = "zombiesaddon.config.waveDelaysRlModeOffset"
        waveDelaysRlModeOffset = addOption(categoryWaveDelays, waveDelaysRlModeOffsetKey, config.get(
            categoryWaveDelays.name,
            "waveDelaysRlModeOffset",
            -28,
            "$waveDelaysRlModeOffsetKey.description",
            -1000, 1000
        )).int

        val waveDelaysWrathModeOffsetKey = "zombiesaddon.config.waveDelaysWrathModeOffset"
        waveDelaysWrathModeOffset = addOption(categoryWaveDelays, waveDelaysWrathModeOffsetKey, config.get(
            categoryWaveDelays.name,
            "waveDelaysWrathModeOffset",
            -40,
            "$waveDelaysWrathModeOffsetKey.description",
            -1000, 1000
        )).int


        // SLA
        val slaAutoSlaKey = "zombiesaddon.config.slaAutoSla"
        slaAutoSla = addOption(categorySla, slaAutoSlaKey, config.get(
            categorySla.name,
            "slaAutoSla",
            false,
            "$slaAutoSlaKey.description"
        )).boolean

        val slaTextColorKey = "zombiesaddon.config.slaTextColor"
        slaTextColor = addOption(categorySla, slaTextColorKey, config.get(
            categorySla.name,
            "slaTextColor",
            true,
            "$slaTextColorKey.description"
        )).boolean

        val slaActivatedWindowsKey = "zombiesaddon.config.slaActivatedWindows"
        slaActivatedWindows = addOption(categorySla, slaActivatedWindowsKey, config.get(
            categorySla.name,
            "slaActivatedWindows",
            true,
            "$slaActivatedWindowsKey.description"
        )).boolean

        val slaUnactivatedWindowsKey = "zombiesaddon.config.slaUnactivatedWindows"
        slaUnactivatedWindows = addOption(categorySla, slaUnactivatedWindowsKey, config.get(
            categorySla.name,
            "slaUnactivatedWindows",
            false,
            "$slaUnactivatedWindowsKey.description"
        )).boolean


        // Auto Rejoin
        val autoRejoinDefaultKey = "zombiesaddon.config.autoRejoinDefault"
        autoRejoinDefault = addOption(categoryAutoRejoin, autoRejoinDefaultKey, config.get(
            categoryAutoRejoin.name,
            "autoRejoinDefault",
            false,
            "$autoRejoinDefaultKey.description"
        )).boolean

        val autoRejoinTextKey = "zombiesaddon.config.autoRejoinText"
        autoRejoinText = addOption(categoryAutoRejoin, autoRejoinTextKey, config.get(
            categoryAutoRejoin.name,
            "autoRejoinText",
            false,
            "$autoRejoinTextKey.description"
        )).boolean


        // Last Weapons
        val lwToggleKey = "zombiesaddon.config.lwToggle"
        lwToggle = addOption(categoryLastWeapons, lwToggleKey, config.get(
            categoryLastWeapons.name,
            "lwToggle",
            true,
            "$lwToggleKey.description"
        )).boolean

        val lwDisplayArmorsKey = "zombiesaddon.config.lwDisplayArmors"
        lwDisplayArmors = addOption(categoryLastWeapons, lwDisplayArmorsKey, config.get(
            categoryLastWeapons.name,
            "lwDisplayArmors",
            true,
            "$lwDisplayArmorsKey.description"
        )).boolean

        val lwDisplayWeaponsLevelKey = "zombiesaddon.config.lwDisplayWeaponsLevel"
        lwDisplayWeaponsLevel = addOption(categoryLastWeapons, lwDisplayWeaponsLevelKey, config.get(
            categoryLastWeapons.name,
            "lwDisplayWeaponsLevel",
            true,
            "$lwDisplayWeaponsLevelKey.description"
        )).boolean

        val lwDisplayCooledDownSkillKey = "zombiesaddon.config.lwDisplayCooledDownSkill"
        lwDisplayCooledDownSkill = addOption(categoryLastWeapons, lwDisplayCooledDownSkillKey, config.get(
            categoryLastWeapons.name,
            "lwDisplayCooledDownSkill",
            true,
            "$lwDisplayCooledDownSkillKey.description"
        )).boolean

        val lwWorkInGameOverKey = "zombiesaddon.config.lwWorkInGameOver"
        lwWorkInGameOver = addOption(categoryLastWeapons, lwWorkInGameOverKey, config.get(
            categoryLastWeapons.name,
            "lwWorkInGameOver",
            true,
            "$lwWorkInGameOverKey.description"
        )).boolean


        // Recorder
        val recorderToggleKey = "zombiesaddon.config.recorderToggle"
        recorderToggle = addOption(categoryRecorder, recorderToggleKey, config.get(
            categoryRecorder.name,
            "recorderToggle",
            true,
            "$recorderToggleKey.description"
        )).boolean

        val recorderDefaultCategoryKey = "zombiesaddon.config.recorderDefaultCategory"
        recorderDefaultCategory = addOption(categoryRecorder, recorderDefaultCategoryKey, config.get(
            categoryRecorder.name,
            "recorderDefaultCategory",
            "general",
            "$recorderDefaultCategoryKey.description"
        )).string

        val recorderPbNoticeKey = "zombiesaddon.config.recorderPbNotice"
        recorderPbNotice = addOption(categoryRecorder, recorderPbNoticeKey, config.get(
            categoryRecorder.name,
            "recorderPbNotice",
            true,
            "$recorderPbNoticeKey.description"
        )).boolean


        // Patcher
        val patcherFixRifleSoundKey = "zombiesaddon.config.patcherFixRifleSound"
        patcherFixRifleSound = addOption(categoryPatcher, patcherFixRifleSoundKey, config.get(
            categoryPatcher.name,
            "patcherFixRifleSound",
            true,
            "$patcherFixRifleSoundKey.description"
        )).boolean

        val patcherKoreanPatchKey = "zombiesaddon.config.patcherKoreanPatch"
        patcherKoreanPatch = addOption(categoryPatcher, patcherKoreanPatchKey, config.get(
            categoryPatcher.name,
            "patcherKoreanPatch",
            false,
            "$patcherKoreanPatchKey.description"
        )).boolean

        val patcherZombiesOverlayInKoKey = "zombiesaddon.config.patcherZombiesOverlayInKo"
        patcherZombiesOverlayInKo = addOption(categoryPatcher, patcherZombiesOverlayInKoKey, config.get(
            categoryPatcher.name,
            "patcherZombiesOverlayInKo",
            true,
            "$patcherZombiesOverlayInKoKey.description"
        )).boolean


        // 다른 모드
        val disableSpawnTimeOfSstKey = "zombiesaddon.config.disableSpawnTimeOfSst"
        disableSpawnTimeOfSst = addOption(categoryOtherMods, disableSpawnTimeOfSstKey, config.get(
            categoryOtherMods.name,
            "disableSpawnTimeOfSst",
            true,
            "$disableSpawnTimeOfSstKey.description"
        )).boolean

        val disableTimerOfZombiesUtilsKey = "zombiesaddon.config.disableTimerOfZombiesUtils"
        disableTimerOfZombiesUtils = addOption(categoryOtherMods, disableTimerOfZombiesUtilsKey, config.get(
            categoryOtherMods.name,
            "disableTimerOfZombiesUtils",
            true,
            "$disableTimerOfZombiesUtilsKey.description"
        )).boolean

        HudUtils.autoSplitsX = config.get("HUD", "autoSplitsX", -1.0).double
        HudUtils.autoSplitsY = config.get("HUD", "autoSplitsY", -1.0).double
        HudUtils.waveDelaysX = config.get("HUD", "waveDelaysX", -1.0).double
        HudUtils.waveDelaysY = config.get("HUD", "waveDelaysY", -1.0).double
        HudUtils.powerupPatternsX = config.get("HUD", "powerupPatternsX", -1.0).double
        HudUtils.powerupPatternsY = config.get("HUD", "powerupPatternsY", -1.0).double
        HudUtils.modNameX = config.get("HUD", "modNameX", -1.0).double
        HudUtils.modNameY = config.get("HUD", "modNameY", -1.0).double
        HudUtils.toggleTextX = config.get("HUD", "toggleTextX", -1.0).double
        HudUtils.toggleTextY = config.get("HUD", "toggleTextY", -1.0).double
    }

    private fun addOption(category: Category, langKey: String, prop: Property): Property {
        prop.languageKey = langKey
        category[prop.name] = ConfigElement(prop)
        return prop
    }

    fun getElements() = listOf(
        DummyCategoryElement(categoryGeneral.name, "zombiesaddon.config.category.general", categoryGeneral.options),
        DummyCategoryElement(categoryModules.name, "zombiesaddon.config.category.modules", categoryModules.options),
        DummyCategoryElement(categoryPv.name, "zombiesaddon.config.category.pv", categoryPv.options),
        DummyCategoryElement(categoryBlockAlarm.name, "zombiesaddon.config.category.blockAlarm", categoryBlockAlarm.options),
        DummyCategoryElement(categoryAutoSplits.name, "zombiesaddon.config.category.autoSplits", categoryAutoSplits.options),
        DummyCategoryElement(categoryInternalTimer.name, "zombiesaddon.config.category.internalTimer", categoryInternalTimer.options),
        DummyCategoryElement(categoryWaveDelays.name, "zombiesaddon.config.category.waveDelays", categoryWaveDelays.options),
        DummyCategoryElement(categoryAutoRejoin.name, "zombiesaddon.config.category.autoRejoin", categoryAutoRejoin.options),
        DummyCategoryElement(categorySla.name, "zombiesaddon.config.category.sla", categorySla.options),
        DummyCategoryElement(categoryLastWeapons.name, "zombiesaddon.config.category.lw", categoryLastWeapons.options),
        DummyCategoryElement(categoryRecorder.name, "zombiesaddon.config.category.recorder", categoryRecorder.options),
        DummyCategoryElement(categoryPatcher.name, "zombiesaddon.config.category.patcher", categoryPatcher.options),
        DummyCategoryElement(categoryOtherMods.name, "zombiesaddon.config.category.otherMods", categoryOtherMods.options)
    )

    private fun save() {
        config.save()
        sync()

        if (!ZombiesAddon.instance.hasSST) return

        runCatching {
            MainConfiguration.config.save()
            ShowSpawnTime.getMainConfiguration().ConfigLoad()
        }
    }

    @SubscribeEvent
    fun onConfigChanged(event: OnConfigChangedEvent) {
        if (event.modID == MODID) save()
    }
}

private data class Category(val name: String) {
    private val map = mutableMapOf<String, IConfigElement>()
    operator fun get(key: String) = map[key]
    operator fun set(key: String, value: IConfigElement) = map.set(key, value)
    val options get() = map.values.toList()
}