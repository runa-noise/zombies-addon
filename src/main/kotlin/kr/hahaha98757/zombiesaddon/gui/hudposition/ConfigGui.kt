package kr.hahaha98757.zombiesaddon.gui.hudposition

import kr.hahaha98757.zombiesaddon.NAME
import kr.hahaha98757.zombiesaddon.VERSION
import kr.hahaha98757.zombiesaddon.config.ZAConfig
import kr.hahaha98757.zombiesaddon.utils.*
import net.minecraft.client.gui.GuiButton
import net.minecraft.client.gui.GuiScreen
import org.lwjgl.input.Mouse

object ConfigGui: GuiScreen() {
    private val boxes = mutableListOf<HudCoordinate>()

    override fun handleMouseInput() {
        super.handleMouseInput()
        val mouseX = Mouse.getEventX() * width / mc.displayWidth
        val mouseY = height - Mouse.getEventY() * height / mc.displayHeight - 1
        val button = Mouse.getEventButton()
        val mouseButtonDown = Mouse.getEventButtonState()

        for (box in boxes) {
            if (box.isMouseOver(mouseX, mouseY) && button == 0)
                if (mouseButtonDown) box.onMousePressed(mouseX, mouseY)
                else box.onMouseReleased()
            if (box.isDragging) box.onMouseDragged(mouseX, mouseY)
        }
    }

    override fun initGui() {
        val widthAutoSplits = fr.getStringWidth("0:00.0")
        val widthWaveDelays = fr.getStringWidth("➤ ${ZAConfig.waveDelays.textStyle}")
        val widthPowerupPatterns = fr.getStringWidth("Shopping Spree: Round 105")
        val widthModName = fr.getStringWidth("$NAME v$VERSION")
        val widthToggleText = fr.getStringWidth("Player Visibility: off")
        val x = getX().toInt() / 2 - 82
        val y = getY().toInt() - 25
        buttonList.add(GuiButton(1, x, y, 80, 20, getTranslatedString("gui.done")))
        buttonList.add(GuiButton(2, x + 85, y, 80, 20, getTranslatedString("zombiesaddon.gui.reset")))
        boxes.clear()
        boxes += HudCoordinate(2, HudUtils.autoSplitsX, HudUtils.autoSplitsY, widthAutoSplits, fr.FONT_HEIGHT, width, height)
        boxes += HudCoordinate(0, HudUtils.waveDelaysX, HudUtils.waveDelaysY, widthWaveDelays, fr.FONT_HEIGHT * 8, width, height)
        boxes += HudCoordinate(1, HudUtils.powerupPatternsX, HudUtils.powerupPatternsY, widthPowerupPatterns, fr.FONT_HEIGHT * 6, width, height)
        boxes += HudCoordinate(3, HudUtils.modNameX, HudUtils.modNameY, widthModName, fr.FONT_HEIGHT, width, height)
        boxes += HudCoordinate(4, HudUtils.toggleTextX, HudUtils.toggleTextY, widthToggleText, fr.FONT_HEIGHT * 3, width, height)
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        drawDefaultBackground()

        for (box in boxes) box.draw()

        fr.drawStringWithShadow(NAME, (getX(NAME) + 1) / 2, 10f, 0xffffff)
        val str = getTranslatedString("zombiesaddon.gui.comment")
        fr.drawStringWithShadow(str, (getX(str) + 1) / 2, height / 2f, 0xffffff)
        super.drawScreen(mouseX, mouseY, partialTicks)
    }

    override fun actionPerformed(button: GuiButton) {
        when (button.id) {
            1 -> {
                for (box in boxes) when (box.contents) {
                    0 -> {
                        if (!box.isDefault) {
                            HudUtils.waveDelaysX = box.x
                            ZAConfig.config.get("HUD", "waveDelaysX", -1).set(box.x)
                        }
                        if (!box.isDefault) {
                            HudUtils.waveDelaysY = box.y
                            ZAConfig.config.get("HUD", "waveDelaysY", -1).set(box.y)
                        }
                    }
                    1 -> {
                        if (!box.isDefault) {
                            HudUtils.powerupPatternsX = box.x
                            ZAConfig.config.get("HUD", "powerupPatternsX", -1).set(box.x)
                        }
                        if (!box.isDefault) {
                            HudUtils.powerupPatternsY = box.y
                            ZAConfig.config.get("HUD", "powerupPatternsY", -1).set(box.y)
                        }
                    }
                    2 -> {
                        if (!box.isDefault) {
                            HudUtils.autoSplitsX = box.x
                            ZAConfig.config.get("HUD", "autoSplitsX", -1).set(box.x)
                        }
                        if (!box.isDefault) {
                            HudUtils.autoSplitsY = box.y
                            ZAConfig.config.get("HUD", "autoSplitsY", -1).set(box.y)
                        }
                    }
                    3 -> {
                        if (!box.isDefault) {
                            HudUtils.modNameX = box.x
                            ZAConfig.config.get("HUD", "modNameX", -1).set(box.x)
                        }
                        if (!box.isDefault) {
                            HudUtils.modNameY = box.y
                            ZAConfig.config.get("HUD", "modNameY", -1).set(box.y)
                        }
                    }
                    4 -> {
                        if (!box.isDefault) {
                            HudUtils.toggleTextX = box.x
                            ZAConfig.config.get("HUD", "toggleTextX", -1).set(box.x)
                        }
                        if (!box.isDefault) {
                            HudUtils.toggleTextY = box.y
                            ZAConfig.config.get("HUD", "toggleTextY", -1).set(box.y)
                        }
                    }
                }
                ZAConfig.config.save()
                mc.displayGuiScreen(null)
            }
            2 -> {
                for (box in boxes) when (box.contents) {
                    0 -> {
                        ZAConfig.config.get("HUD", "waveDelaysX", -1).set(-1)
                        ZAConfig.config.get("HUD", "waveDelaysY", -1).set(-1)
                        HudUtils.waveDelaysX = -1.0
                        HudUtils.waveDelaysY = -1.0
                        box.isDefault = true
                        DelayedTask {
                            box.x = HudUtils.waveDelaysX
                            box.absoluteX = (HudUtils.waveDelaysX * width.toDouble()).toInt()
                            box.y = HudUtils.waveDelaysY
                            box.absoluteY = (HudUtils.waveDelaysY * width.toDouble()).toInt()
                        }
                    }
                    1 -> {
                        ZAConfig.config.get("HUD", "powerupPatternsX", -1).set(-1)
                        ZAConfig.config.get("HUD", "powerupPatternsY", -1).set(-1)
                        HudUtils.powerupPatternsX = -1.0
                        HudUtils.powerupPatternsY = -1.0
                        box.isDefault = true
                        DelayedTask {
                            box.x = HudUtils.powerupPatternsX
                            box.absoluteX = (HudUtils.powerupPatternsX * width.toDouble()).toInt()
                            box.y = HudUtils.powerupPatternsY
                            box.absoluteY = (HudUtils.powerupPatternsY * width.toDouble()).toInt()
                        }
                    }
                    2 -> {
                        ZAConfig.config.get("HUD", "autoSplitsX", -1).set(-1)
                        ZAConfig.config.get("HUD", "autoSplitsY", -1).set(-1)
                        HudUtils.autoSplitsX = -1.0
                        HudUtils.autoSplitsY = -1.0
                        box.isDefault = true
                        DelayedTask {
                            box.x = HudUtils.autoSplitsX
                            box.absoluteX = (HudUtils.autoSplitsX * width.toDouble()).toInt()
                            box.y = HudUtils.autoSplitsY
                            box.absoluteY = (HudUtils.autoSplitsY * width.toDouble()).toInt()
                        }
                    }
                    3 -> {
                        ZAConfig.config.get("HUD", "modNameX", -1).set(-1)
                        ZAConfig.config.get("HUD", "modNameY", -1).set(-1)
                        HudUtils.modNameX = -1.0
                        HudUtils.modNameY = -1.0
                        box.isDefault = true
                        DelayedTask {
                            box.x = HudUtils.modNameX
                            box.absoluteX = (HudUtils.modNameX * width.toDouble()).toInt()
                            box.y = HudUtils.modNameY
                            box.absoluteY = (HudUtils.modNameY * width.toDouble()).toInt()
                        }
                    }
                    4 -> {
                        ZAConfig.config.get("HUD", "toggleTextX", -1).set(-1)
                        ZAConfig.config.get("HUD", "toggleTextY", -1).set(-1)
                        HudUtils.toggleTextX = -1.0
                        HudUtils.toggleTextY = -1.0
                        box.isDefault = true
                        DelayedTask {
                            box.x = HudUtils.toggleTextX
                            box.absoluteX = (HudUtils.toggleTextX * width.toDouble()).toInt()
                            box.y = HudUtils.toggleTextY
                            box.absoluteY = (HudUtils.toggleTextY * width.toDouble()).toInt()
                        }
                    }
                }
                ZAConfig.config.save()
            }
            else -> ZAConfig.config.save()
        }
    }
}