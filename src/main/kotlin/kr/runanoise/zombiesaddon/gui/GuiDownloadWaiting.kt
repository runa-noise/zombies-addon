package kr.runanoise.zombiesaddon.gui

import kr.runanoise.zombiesaddon.utils.getTranslatedString
import net.minecraft.client.gui.GuiButton
import net.minecraft.client.gui.GuiScreen
import org.lwjgl.input.Keyboard

object GuiDownloadWaiting: GuiScreen() {
    var failed = false
    lateinit var backButton: GuiButton

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        backButton.visible = failed

        drawDefaultBackground()
        if (!failed) drawCenteredString(fontRendererObj, getTranslatedString("zombiesaddon.gui.downloadWaiting.title"), width / 2, height / 2 - 40, 0xffffff)
        else drawCenteredString(fontRendererObj, getTranslatedString("zombiesaddon.gui.downloadWaiting.failed"), width / 2, height / 2 - 40, 0xff5555)
        super.drawScreen(mouseX, mouseY, partialTicks)
    }

    override fun initGui() {
        buttonList.clear()
        backButton = GuiButton(0, width / 2 - 100, height / 2, getTranslatedString("gui.back"))
        buttonList.add(backButton)
    }

    override fun actionPerformed(button: GuiButton) {
        failed = false
        mc.displayGuiScreen(GuiUpdateScreen)
    }

    override fun keyTyped(typedChar: Char, keyCode: Int) {
        if (keyCode != Keyboard.KEY_ESCAPE) super.keyTyped(typedChar, keyCode)
    }
}