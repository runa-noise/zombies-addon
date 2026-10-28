package kr.runanoise.zombiesaddon

import net.minecraft.client.settings.KeyBinding
import net.minecraftforge.fml.client.registry.ClientRegistry
import org.lwjgl.input.Keyboard

object KeyBindings {
    val togglePv = KeyBinding("zombiesaddon.key.togglePlayerVisibility", Keyboard.KEY_Z, NAME)
    val toggleBlockAlarm = KeyBinding("zombiesaddon.key.toggleBlockAlarm", Keyboard.KEY_P, NAME)
    val toggleAutoRejoin = KeyBinding("zombiesaddon.key.toggleAutoRejoin", Keyboard.KEY_O, NAME)

    val zsvScrollUp = KeyBinding("zombiesaddon.key.zsvScrollUp", Keyboard.KEY_LBRACKET, NAME)
    val zsvScrollDown = KeyBinding("zombiesaddon.key.zsvScrollDown", Keyboard.KEY_RBRACKET, NAME)

    val toggleRlMode = KeyBinding("zombiesaddon.key.toggleRlMode", Keyboard.KEY_UP, NAME)
    val toggleWrathMode = KeyBinding("zombiesaddon.key.toggleWrathMode", Keyboard.KEY_DOWN, NAME)

    val textMacro = KeyBinding("zombiesaddon.key.textMacro", Keyboard.KEY_Q, NAME)

    val insTimer = KeyBinding("zombiesaddon.key.insTimer", Keyboard.KEY_NUMPAD1, NAME)
    val maxTimer = KeyBinding("zombiesaddon.key.maxTimer", Keyboard.KEY_NUMPAD2, NAME)
    val ssTimer = KeyBinding("zombiesaddon.key.ssTimer", Keyboard.KEY_NUMPAD3, NAME)
    val dgTimer = KeyBinding("zombiesaddon.key.dgTimer", Keyboard.KEY_NUMPAD4, NAME)
    val carTimer = KeyBinding("zombiesaddon.key.carTimer", Keyboard.KEY_NUMPAD5, NAME)
    val bgTimer = KeyBinding("zombiesaddon.key.bgTimer", Keyboard.KEY_NUMPAD6, NAME)
    val autoTimer = KeyBinding("zombiesaddon.key.autoTimer", Keyboard.KEY_F, NAME)

    fun registerAll() {
        ClientRegistry.registerKeyBinding(togglePv)
        ClientRegistry.registerKeyBinding(toggleBlockAlarm)
        ClientRegistry.registerKeyBinding(toggleAutoRejoin)
        ClientRegistry.registerKeyBinding(zsvScrollUp)
        ClientRegistry.registerKeyBinding(zsvScrollDown)
        ClientRegistry.registerKeyBinding(toggleRlMode)
        ClientRegistry.registerKeyBinding(toggleWrathMode)
        ClientRegistry.registerKeyBinding(textMacro)
        ClientRegistry.registerKeyBinding(insTimer)
        ClientRegistry.registerKeyBinding(maxTimer)
        ClientRegistry.registerKeyBinding(ssTimer)
        ClientRegistry.registerKeyBinding(dgTimer)
        ClientRegistry.registerKeyBinding(carTimer)
        ClientRegistry.registerKeyBinding(bgTimer)
        ClientRegistry.registerKeyBinding(autoTimer)
    }
}