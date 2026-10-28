package kr.runanoise.zombiesaddon.modules

import kr.runanoise.zombiesaddon.KeyBindings
import kr.runanoise.zombiesaddon.config.ZAConfig
import kr.runanoise.zombiesaddon.events.RoundStartEvent
import kr.runanoise.zombiesaddon.utils.RepeatedTask
import kr.runanoise.zombiesaddon.utils.Scoreboard
import kr.runanoise.zombiesaddon.utils.addTranslatedChat
import kr.runanoise.zombiesaddon.utils.sendChat

object AutoRejoin: ToggleableModule("Auto Rejoin", ZAConfig.autoRejoin.default) {
    override fun getKeyBinding() = KeyBindings.toggleAutoRejoin
    override fun addToggleText(enabled: Boolean) =
        addTranslatedChat("zombiesaddon.modules.general.toggled", "Auto Rejoin", if (enabled) "§aon" else "§coff")

    override fun onRoundStart(event: RoundStartEvent) {
        addTranslatedChat("zombiesaddon.modules.autoRejoin.rejoining")
        sendChat("/l")
        RepeatedTask(ZAConfig.autoRejoin.durationTimes, endTask = { sendChat("/rejoin") }) {
            if (Scoreboard.isNotZombies) {
                sendChat("/rejoin")
                RepeatedTask.stop()
            }
        }
    }
}