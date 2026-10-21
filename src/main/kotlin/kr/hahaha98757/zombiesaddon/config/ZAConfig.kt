package kr.hahaha98757.zombiesaddon.config

import kr.hahaha98757.zombiesaddon.MODID
import kr.hahaha98757.zombiesaddon.config.categories.*
import kr.hahaha98757.zombiesaddon.utils.HudUtils
import net.minecraftforge.common.config.Configuration
import net.minecraftforge.fml.client.event.ConfigChangedEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

object ZAConfig {
    lateinit var config: Configuration

    val general = General()
    val modules = Modules()
    val pv = PlayerVisibility()
    val blockAlarm = BlockAlarm()
    val autoSplits = AutoSplits()
    val internalTimer = InternalTimer()
    val waveDelays = WaveDelays()
    val sla = SpawnLimitAction()
    val autoRejoin = AutoRejoin()
    val lw = LastWeapons()
    val recorder = Recorder()
    val patcher = Patcher()
    val otherMods = OtherMods()


    fun init(config: Configuration) {
        this.config = config
        config.load()
        sync()
    }

    fun sync() {
        general.sync(config)
        modules.sync(config)
        pv.sync(config)
        blockAlarm.sync(config)
        autoSplits.sync(config)
        internalTimer.sync(config)
        waveDelays.sync(config)
        sla.sync(config)
        autoRejoin.sync(config)
        lw.sync(config)
        recorder.sync(config)
        patcher.sync(config)
        otherMods.sync(config)

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

    fun save() {
        config.save()
        sync()
    }

    @SubscribeEvent
    fun onConfigChanged(event: ConfigChangedEvent.OnConfigChangedEvent) {
        if (event.modID == MODID) save()
    }

    fun getElements() = listOf(
        general.getCategoryElement(),
        modules.getCategoryElement(),
        pv.getCategoryElement(),
        blockAlarm.getCategoryElement(),
        autoSplits.getCategoryElement(),
        internalTimer.getCategoryElement(),
        waveDelays.getCategoryElement(),
        sla.getCategoryElement(),
        autoRejoin.getCategoryElement(),
        lw.getCategoryElement(),
        recorder.getCategoryElement(),
        patcher.getCategoryElement(),
        otherMods.getCategoryElement()
    )
}