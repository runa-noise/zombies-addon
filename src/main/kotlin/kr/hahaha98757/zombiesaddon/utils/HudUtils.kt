package kr.hahaha98757.zombiesaddon.utils

import kr.hahaha98757.zombiesaddon.NAME
import kr.hahaha98757.zombiesaddon.VERSION
import kr.hahaha98757.zombiesaddon.config.ZAConfig

object HudUtils {
    private var _autoSplitsX = 0.0
    var autoSplitsX = 0.0
        get() = if (field < 0) 1.0 - (fr.getStringWidth("0:00.0") + 1) / getX() else field
        set(value) {
            field = value
            _autoSplitsX = value
        }
    private var _autoSplitsY = 0.0
    var autoSplitsY = 0.0
        get() = if (field < 0) 1.0 - (fr.FONT_HEIGHT + 1) / getY() else field
        set(value) {
            field = value
            _autoSplitsY = value
        }

    private var _waveDelaysX = 0.0
    var waveDelaysX = 0.0
        get() = if (field < 0) 1.0 - (fr.getStringWidth("➤ ${ZAConfig.waveDelays.textStyle}") + 0.9) / getX() else field
        set(value) {
            field = value
            _waveDelaysX = value
        }
    private var _waveDelaysY = 0.0
    var waveDelaysY = 0.0
        get() = if (field < 0) 1.0 - (fr.FONT_HEIGHT*9 + 1) / getY() else field
        set(value) {
            field = value
            _waveDelaysY = value
        }

    private var _powerupPatternsX = 0.0
    var powerupPatternsX = 0.0
        get() = if (field < 0) 1.0 - (fr.getStringWidth("Shopping Spree: Round 105") + 1) / getX() else field
        set(value) {
            field = value
            _powerupPatternsX = value
        }
    private var _powerupPatternsY = 0.0
    var powerupPatternsY = 0.0
        get() = if (field < 0) 1.0 - (fr.FONT_HEIGHT*15 + 1) / getY() else field
        set(value) {
            field = value
            _powerupPatternsY = value
        }

    private var _modNameX = 0.0
    var modNameX = 0.0
        get() = if (field < 0) 1.0 - (fr.getStringWidth("$NAME v$VERSION") + 0.9) / getX() else field
        set(value) {
            field = value
            _modNameX = value
        }
    private var _modNameY = 0.0
    var modNameY = 0.0
        get() = if (field < 0) 0.0 + 1.1 / getY() else field
        set(value) {
            field = value
            _modNameY = value
        }
    private var _toggleTextX = 0.0
    var toggleTextX = 0.0
        get() = if (field < 0) 1.0 - (fr.getStringWidth("Player Visibility: off") + 1) / getX() else field
        set(value) {
            field = value
            _toggleTextX = value
        }
    private var _toggleTextY = 0.0
    var toggleTextY = 0.0
        get() = if (field < 0) (fr.FONT_HEIGHT + 1) / getY().toDouble() else field
        set(value) {
            field = value
            _toggleTextY = value
        }

    private val regex1 = Regex("^W[1-9]:? \\d+:\\d{2}\\.\\d$")
    private val regex2 = Regex("^W[1-9]:? \\d{2}:\\d{2}$")

    fun getAutoSplitsStrX(str: String): Float = if (_autoSplitsX < 0) getX(str) else autoSplitsX.toFloat() * getX()
    fun getAutoSplitsStrY(): Float = if (_autoSplitsY < 0) getYFont() else autoSplitsY.toFloat() * getY()

    fun getWaveDelaysStrX(str: String): Float {
        val noColor = str.withoutColor()
        if (noColor.matches(regex1) || noColor.matches(regex2))
            return if (_waveDelaysX < 0) getX(noColor) else waveDelaysX.toFloat() * getX() + fr.getStringWidth("➤ ")
        return if (_waveDelaysX < 0) getX(noColor) else waveDelaysX.toFloat() * getX()
    }
    fun getWaveDelaysStrY(length: Int, index: Int): Float = if (_waveDelaysY < 0) getYFont() - fr.FONT_HEIGHT * (length - index) else waveDelaysY.toFloat() * getY() + fr.FONT_HEIGHT * (8 - (length - index))

    fun getPowerupPatternsStrX(str: String): Float = if (_powerupPatternsX < 0) getX(str)
    else if (_powerupPatternsY < 0.5 - fr.getStringWidth("Shopping Spree: Round 105") / 2 / getX()) powerupPatternsX.toFloat() * getX()
    else (powerupPatternsX * getX() + fr.getStringWidth("Shopping Spree: Round 105") - fr.getStringWidth(str)).toFloat()
    fun getPowerupPatternsStrY(i: Int): Float = if (_powerupPatternsY < 0) getYFont() - fr.FONT_HEIGHT * (15 - i) else powerupPatternsY.toFloat() * getY() + fr.FONT_HEIGHT * (i - 1)

    fun getModNameStrX(str: String): Float = if (_modNameX < 0) getX(str) else modNameX.toFloat() * getX()
    fun getModNameStrY(): Float = if (_modNameY < 0) 1f else modNameY.toFloat() * getY()

    fun getToggleTextStrX(str: String): Float = if (_toggleTextX < 0) getX(str)
    else if (_toggleTextX < 0.5 - fr.getStringWidth("Player Visibility: off") / 2 / getX()) toggleTextX.toFloat() * getX()
    else (toggleTextX * getX() + fr.getStringWidth("Player Visibility: off") - fr.getStringWidth(str)).toFloat()
    fun getToggleTextStrY(i: Int): Float = if (_toggleTextY < 0) fr.FONT_HEIGHT * i + 1f else toggleTextY.toFloat() * getY() + fr.FONT_HEIGHT * (i - 1)
}