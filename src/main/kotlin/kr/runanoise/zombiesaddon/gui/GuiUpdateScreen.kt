package kr.runanoise.zombiesaddon.gui

import kr.runanoise.zombiesaddon.update.AutoUpdater
import kr.runanoise.zombiesaddon.utils.getTranslatedString
import net.minecraft.client.gui.GuiButton
import net.minecraft.client.gui.GuiConfirmOpenLink
import net.minecraft.client.gui.GuiScreen
import org.lwjgl.input.Keyboard
import java.awt.Desktop
import java.net.URI

object GuiUpdateScreen: GuiScreen() {
    private var requiredUpdates = false

    fun getInstance(required: Boolean): GuiUpdateScreen {
        requiredUpdates = required
        return this
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        val str = getTranslatedString(if (requiredUpdates) "zombiesaddon.gui.updateScreen.title.mandatory" else "zombiesaddon.gui.updateScreen.title.normal")
        drawDefaultBackground()
        drawCenteredString(fontRendererObj, str, width / 2, height / 2 - 40, 0xffffff)
        if (requiredUpdates) drawCenteredString(fontRendererObj, getTranslatedString("zombiesaddon.gui.updateScreen.message.mandatory"), width/2, height / 2 - 31, 0xff5555)
        super.drawScreen(mouseX, mouseY, partialTicks)
    }

    override fun initGui() {
        buttonList.clear()
        buttonList.add(GuiButton(0, width / 2 - 100, height / 2, getTranslatedString("zombiesaddon.gui.updateScreen.button.autoupdate")))
        buttonList.add(GuiButton(1, width / 2 - 100, height / 2 + 30, getTranslatedString("zombiesaddon.gui.updateScreen.button.url")))
        if (!requiredUpdates) buttonList.add(GuiButton(2, width / 2 - 100, height / 2 + 60, getTranslatedString("zombiesaddon.gui.updateScreen.button.continue")))
        buttonList.add(GuiButton(3, width / 2 - 100, height / 2 + if (requiredUpdates) 60 else 90, getTranslatedString("menu.quit")))
    }

    override fun actionPerformed(button: GuiButton) {
        when (button.id) {
            0 -> {
                mc.displayGuiScreen(GuiDownloadWaiting)
                AutoUpdater.autoUpdate()
            }
            1 -> mc.displayGuiScreen(GuiConfirmOpenLink(this, "https://github.com/runa-noise/zombies-addon/releases", 0, true))
            2 -> mc.displayGuiScreen(null)
            3 -> mc.shutdown()
        }
    }

    override fun confirmClicked(result: Boolean, id: Int) {
        if (result) try {
            Desktop.getDesktop().browse(URI("https://github.com/runa-noise/zombies-addon/releases"))
        } catch (e: Exception) {
            e.printStackTrace()
        } catch (e: NoClassDefFoundError) {
            e.printStackTrace()
        }
        mc.displayGuiScreen(this)
    }

    override fun keyTyped(typedChar: Char, keyCode: Int) {
        if (keyCode != Keyboard.KEY_ESCAPE) super.keyTyped(typedChar, keyCode)
    }
}