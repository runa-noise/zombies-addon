package kr.hahaha98757.zombiesaddon.data

import kr.hahaha98757.zombiesaddon.config.ZAConfig

class Room(val name: String, private val alias: String, val windows: Array<Window>) {
    var activeWindows = 0

    override fun toString(): String {
        if (!ZAConfig.sla.showActiveWindows && !ZAConfig.sla.showInactiveWindows) return "${color('6')}$name${color('e')}: ${color('d')}$activeWindows"
        val strBuilder = StringBuilder("${color('6')}$name${color('d')} $activeWindows${color('e')}:")
        for (window in windows)
            if (!window.active && ZAConfig.sla.showInactiveWindows) strBuilder.append("${color('c')} $alias${window.id}")
            else if (window.active && ZAConfig.sla.showActiveWindows) strBuilder.append("${color('2')} $alias${window.id}")
        return strBuilder.toString()
    }

    private fun color(code: Char): String = if (ZAConfig.sla.textColor) "§$code" else ""
}