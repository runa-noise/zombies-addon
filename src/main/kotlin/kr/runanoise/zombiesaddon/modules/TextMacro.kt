package kr.runanoise.zombiesaddon.modules

import kr.runanoise.zombiesaddon.KeyBindings
import kr.runanoise.zombiesaddon.config.ZAConfig
import kr.runanoise.zombiesaddon.utils.isDisable
import kr.runanoise.zombiesaddon.utils.isNotPlayZombies
import kr.runanoise.zombiesaddon.utils.sendChat
import net.minecraftforge.fml.common.gameevent.InputEvent

object TextMacro: AlwaysEnableModule("Text Macro") {
    override fun onKeyInput(event: InputEvent.KeyInputEvent) {
        if (!KeyBindings.textMacro.isPressed) return
        if (isDisable()) return
        if (isNotPlayZombies()) return
        sendChat(ZAConfig.modules.textMacro)
    }
}