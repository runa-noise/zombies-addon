package kr.hahaha98757.zombiesaddon.modules

import kr.hahaha98757.zombiesaddon.KeyBindings
import kr.hahaha98757.zombiesaddon.config.ZAConfig
import kr.hahaha98757.zombiesaddon.data.CustomPlaySoundLoader
import kr.hahaha98757.zombiesaddon.data.Wave
import kr.hahaha98757.zombiesaddon.enums.*
import kr.hahaha98757.zombiesaddon.events.LastClientTickEvent
import kr.hahaha98757.zombiesaddon.game.GameManager
import kr.hahaha98757.zombiesaddon.game.TimerSource
import kr.hahaha98757.zombiesaddon.utils.*
import net.minecraftforge.client.event.RenderGameOverlayEvent
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent


object WaveDelays: Module("Wave Delays") {
    private const val DESPAWN_TICK: Short = 6000
    private var offsetMode = OffsetMode.OFF
    private var offset = 0

    override fun onKeyInput(event: KeyInputEvent) {
        if (KeyBindings.toggleRlMode.isPressed) {
            if (isDisable()) return
            if (!isEnable()) return

            offset = if (offsetMode != OffsetMode.RL_MODE) ZAConfig.waveDelays.rlModeOffset else 0
            addChat("§eWave Delays: " + getTranslatedString("zombiesaddon.modules.general.toggled", true, "Offset Mode", if (offsetMode != OffsetMode.RL_MODE) "§aRL Mode" else "§coff"))
            offsetMode = if (offsetMode != OffsetMode.RL_MODE) OffsetMode.RL_MODE else OffsetMode.OFF
        }
        if (KeyBindings.toggleWrathMode.isPressed) {
            if (isDisable()) return
            if (!isEnable()) return

            offset = if (offsetMode != OffsetMode.WRATH_MODE) ZAConfig.waveDelays.wrathModeOffset else 0
            addChat("§eWave Delays: " + getTranslatedString("zombiesaddon.modules.general.toggled", true, "Offset Mode", if (offsetMode != OffsetMode.WRATH_MODE) "§aWrath Mode" else "§coff"))
            offsetMode = if (offsetMode != OffsetMode.WRATH_MODE) OffsetMode.WRATH_MODE else OffsetMode.OFF
        }
    }

    override fun onRender(event: RenderGameOverlayEvent.Text) {
        if (isNotPlayZombies()) return
        val game = GameManager.game ?: return

        val round = game.round
        val roundData = game.roundData
        val waves = roundData?.waves ?: return
        val roundTicks = game.timer.roundTick
        val size = waves.size
        var faded: Boolean
        var color = "§e"

        if (ZAConfig.waveDelays.highlightStyle == HighlightStyle.ZOMBIES_ADDON) for (i in waves.indices.reversed()) {
            val waveTicks = waves[i].ticks + offset
            if (roundTicks >= waveTicks) {
                if (ZAConfig.waveDelays.hidePassedWave && roundTicks > waveTicks) break
                val str = "➤ ${ZAConfig.waveDelays.textStyle}"
                fr.drawStringWithShadow("§5➤ ", HudUtils.getWaveDelaysStrX(str), HudUtils.getWaveDelaysStrY(size, i), 0)
                break
            }
        }

        for ((i, wave) in waves.withIndex()) {
            val waveTicks = wave.ticks + offset

            val bossColor = arrayOf("", "")
            for (prefix in wave.prefixes) {
                if (!ZAConfig.waveDelays.bossColor) break
                if (prefix != Prefix.BOSS && prefix != Prefix.GIANT && prefix != Prefix.OLD_ONE) continue
                bossColor[0] = getBossColor1(game.gameMode, round, i+1)
                if (game.gameMode.map == ZombiesMap.ALIEN_ARCADIUM) bossColor[1] = getBossColor2(round, i+1)
            }

            val waveText = when (ZAConfig.waveDelays.textStyle) {
                WDTextStyle.ZOMBIES_ADDON -> "W${i+1}: ${bossColor[0] + getMinutesString(waveTicks)}:${bossColor[1] + getSecondsString(waveTicks)}.${getTenthSecondsString(waveTicks)}"
                WDTextStyle.DETAILS -> "W${i+1}: ${bossColor[0] + getMinutesString(waveTicks)}:${bossColor[1] + getSecondsString(waveTicks)}.${getHundredthSecondsString(waveTicks)}"
                WDTextStyle.SST -> "W${i+1} ${bossColor[0] + getMinutesString(waveTicks, true)}:${bossColor[1] + getSecondsString(waveTicks)}"
            }

            if (ZAConfig.waveDelays.highlightStyle == HighlightStyle.ZOMBIES_ADDON) {
                if (roundTicks >= waveTicks + DESPAWN_TICK)
                    fr.drawStringWithShadow("§c$waveText", HudUtils.getWaveDelaysStrX(waveText), HudUtils.getWaveDelaysStrY(size, i), 0)
                else if (roundTicks >= waveTicks) if (roundTicks != waveTicks && ZAConfig.waveDelays.hidePassedWave) continue
                    else fr.drawStringWithShadow("§a$waveText", HudUtils.getWaveDelaysStrX(waveText), HudUtils.getWaveDelaysStrY(size, i), 0)
                else if (roundTicks > waveTicks - 60)
                    fr.drawStringWithShadow("§e$waveText", HudUtils.getWaveDelaysStrX(waveText), HudUtils.getWaveDelaysStrY(size, i), 0)
                else
                    fr.drawStringWithShadow("§8$waveText", HudUtils.getWaveDelaysStrX(waveText), HudUtils.getWaveDelaysStrY(size, i), 0)

                drawPrefixes(waveText, wave, i, size)
            } else if (ZAConfig.waveDelays.highlightStyle == HighlightStyle.ZOMBIES_UTILS) {
                faded = if (roundTicks > waveTicks) if (!ZAConfig.waveDelays.hidePassedWave) true else continue else false
                fr.drawStringWithShadow(if (faded) "§8$waveText" else color + waveText, HudUtils.getWaveDelaysStrX(waveText), HudUtils.getWaveDelaysStrY(size, i), 0)

                drawPrefixes(waveText, wave, i, size, faded)
                if (!faded) color = "§7"
            }
        }
    }

    private fun drawPrefixes(waveText: String, wave: Wave, i: Int, length: Int, faded: Boolean = false) {
        if (!ZAConfig.waveDelays.prefix) return
        var width = HudUtils.getWaveDelaysStrX("➤ $waveText")
        for (prefix in wave.prefixes) {
            if (ZAConfig.waveDelays.bossColor && (prefix == Prefix.BOSS || prefix == Prefix.GIANT || prefix == Prefix.OLD_ONE)) continue
            val prefixStr = "${prefix.prefix} "
            width -= fr.getStringWidth(prefixStr)
            fr.drawStringWithShadow(prefixStr, width, HudUtils.getWaveDelaysStrY(length, i), if (faded) prefix.fadedColor else prefix.color)
        }
    }

    override fun onLastTick(event: LastClientTickEvent) {
        if (ZAConfig.internalTimer.mode == Mode.CLIENT) {
            playSound()
            return
        }
        if (isNotPlayZombies()) return
        val source = GameManager.game?.timer?.source ?: return
        if (source != TimerSource.SERVER) playSound()
    }

    fun playSound() {
        if (isNotPlayZombies()) return
        val game = GameManager.game ?: return
        if (game.gameEnd) return

        val roundData = game.roundData

        val waves = roundData?.waves ?: return
        val roundTicks = game.timer.roundTick
        val lastIndex = waves.lastIndex

        for ((i, wave) in waves.withIndex()) {
            val waveTime = wave.ticks + offset
            var play = false

            if (ZAConfig.waveDelays.customPlaySound) {
                val cpsArr = CustomPlaySoundLoader.cps ?: return
                val pre = roundTicks - waveTime

                for (cps in cpsArr) {
                    if (cps.timing != pre) continue
                    play = true
                    when (cps.playType) {
                        1 -> if (lastIndex != i) mc.thePlayer.playSound(cps.name, 1f, cps.pitch)
                        2 -> if (lastIndex == i) mc.thePlayer.playSound(cps.name, 1f, cps.pitch)
                        else -> mc.thePlayer.playSound(cps.name, 1f, cps.pitch)
                    }
                }
            } else {
                val timings = ZAConfig.waveDelays.playSounds
                val pre = roundTicks - waveTime

                if (pre in timings) {
                    play = true
                    mc.thePlayer.playSound("note.pling", 1f, 2.0f)
                }
            }
            if (play) break
        }
    }

    private fun getMinutesString(ticks: Int, sst: Boolean = false): String {
        var minutes = ticks / 1200
        if (minutes < 0) minutes = 0
        return String.format(if (sst) "%02d" else "%d", minutes)
    }

    private fun getSecondsString(ticks: Int): String {
        var seconds = (ticks % 1200) / 20
        if (seconds < 0) seconds = 0
        return String.format("%02d", seconds)
    }

    private fun getTenthSecondsString(ticks: Int): String {
        var tenthSeconds = ((ticks % 1200) % 20) / 2
        if (tenthSeconds < 0) tenthSeconds = 0
        return String.format("%d", tenthSeconds)
    }
    private fun getHundredthSecondsString(ticks: Int): String {
        var hundredthSeconds = ((ticks % 1200) % 20) * 5
        if (hundredthSeconds < 0) hundredthSeconds = 0
        return String.format("%02d", hundredthSeconds)
    }

    override fun isEnable() = ZAConfig.waveDelays.enable
}

private fun getBossColor1(gameMode: GameMode, round: Int, wave: Int): String {
    return when (gameMode.map) {
        ZombiesMap.DEAD_END -> {
            when (round) {
                5 -> {
                    if (gameMode.difficulty == Difficulty.RIP && wave == 3) "§6"
                    else ""
                }
                10 -> {
                    if (wave == 3) "§6"
                    else if (gameMode.difficulty == Difficulty.RIP && wave == 4) "§6"
                    else ""
                }
                15 -> {
                    if (gameMode.difficulty != Difficulty.NORMAL && wave == 2) "§6"
                    else if (gameMode.difficulty == Difficulty.RIP && wave == 3) "§c"
                    else ""
                }
                20 -> {
                    if (wave == 3) "§c"
                    else if (gameMode.difficulty == Difficulty.RIP && wave == 4) "§c"
                    else ""
                }
                25 -> {
                    when (gameMode.difficulty) {
                        Difficulty.HARD if wave == 3 -> "§6"
                        Difficulty.RIP if (wave == 1 || wave == 2) -> "§6"
                        Difficulty.RIP if wave == 3 -> "§5"
                        else -> ""
                    }
                }
                30 -> {
                    if (wave == 3) "§5"
                    else if (gameMode.difficulty != Difficulty.NORMAL && wave == 2) "§5"
                    else if (gameMode.difficulty == Difficulty.RIP) "§5"
                    else ""
                }
                else -> ""
            }
        }
        ZombiesMap.BAD_BLOOD -> {
            when (round) {
                10 -> {
                    if (wave == 3) "§a"
                    else ""
                }
                15 -> {
                    if (gameMode.difficulty == Difficulty.RIP) "§a"
                    else ""
                }
                20 -> {
                    if (gameMode.difficulty != Difficulty.RIP && wave == 2) "§5"
                    else if (gameMode.difficulty == Difficulty.RIP && (wave == 1 || wave == 3)) "§5"
                    else ""
                }
                25 -> {
                    if (gameMode.difficulty == Difficulty.RIP && wave == 3) "§a"
                    else ""
                }
                30 -> {
                    if (gameMode.difficulty != Difficulty.RIP && wave == 2) "§5"
                    else if (gameMode.difficulty == Difficulty.RIP && (wave == 1 || wave == 2)) "§5"
                    else ""
                }
                else -> ""
            }
        }
        ZombiesMap.ALIEN_ARCADIUM -> {
            when (round) {
                15 -> {
                    if (wave == 6) "§3"
                    else ""
                }
                20 -> {
                    if (wave == 3 || wave == 5) "§3"
                    else ""
                }
                22 -> {
                    if (wave == 4 || wave == 6) "§3"
                    else ""
                }
                24, 43 -> {
                    if (wave == 2 || wave == 4 || wave == 6) "§3"
                    else ""
                }
                25 -> "§a"
                30, 42, 44 -> "§3"
                35, 59, in 102..105 -> "§4"
                in 36..39, 41 -> {
                    if (wave == 2 || wave == 3) "§3"
                    else ""
                }
                40 -> {
                    when (wave) {
                        2, 3 -> "§3"
                        5 -> "§4"
                        else -> ""
                    }
                }
                45 -> {
                    when (wave) {
                        2 -> "§3"
                        3, 4 -> "§4"
                        else -> ""
                    }
                }
                46, 48 -> {
                    if (wave == 4) "§4"
                    else ""
                }
                47 -> {
                    if (wave == 3) "§3"
                    else ""
                }
                in 50..53 -> {
                    if (wave == 2 || wave == 4) "§3"
                    else ""
                }
                54, 58 -> {
                    when (wave) {
                        2, 4 -> "§3"
                        5 -> "§4"
                        else -> ""
                    }
                }
                55 -> {
                    when (wave) {
                        in 1..5 -> "§3"
                        6 -> "§4"
                        else -> ""
                    }
                }
                56 -> {
                    if (wave == 1) "§a"
                    else ""
                }
                57 -> {
                    if (wave == 1) "§4"
                    else ""
                }
                60 -> {
                    if (wave == 3 || wave == 4) "§4"
                    else ""
                }
                64, 68, 69, 78, 79, 88, 89, 98, 99 -> {
                    if (wave == 5 || wave == 6) "§4"
                    else ""
                }
                74, 84, 94 -> {
                    if (wave in 4..6) "§4"
                    else ""
                }
                65, 75, 85, 95 -> {
                    if (wave in 4..6) "§3"
                    else ""
                }
                67, 77, 87, 97 -> {
                    if (wave == 6) "§4"
                    else ""
                }
                70, 80, 90, 100 -> {
                    when (wave) {
                        in 4..6 -> "§3"
                        2, 3 -> "§4"
                        else -> ""
                    }
                }
                101 -> "§0"
                else -> ""
            }
        }
        ZombiesMap.PRISON -> {
            when (round) {
                10, 20 -> {
                    if (wave == 3) "§c"
                    else ""
                }
                30 -> "§6"
                31 -> {
                    if (wave == 5) "§6"
                    else ""
                }
                else -> ""
            }
        }
    }
}

private fun getBossColor2(round: Int, wave: Int) = when (round) {
    54, 58 -> {
        if (wave == 2) "§4"
        else ""
    }
    55 -> {
        if (wave == 5) "§4"
        else ""
    }
    70, 80, 90, 100 -> {
        if (wave in 4..6) "§4"
        else ""
    }
    else -> ""
}