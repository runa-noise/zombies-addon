package kr.runanoise.zombiesaddon.gui.hudposition

import kr.runanoise.zombiesaddon.NAME
import kr.runanoise.zombiesaddon.VERSION
import kr.runanoise.zombiesaddon.config.ZAConfig
import kr.runanoise.zombiesaddon.utils.fr
import kr.runanoise.zombiesaddon.utils.getX
import net.minecraft.client.gui.Gui

class HudCoordinate(val contents: Int, var x: Double, var y: Double, private val width: Int, private val height: Int, private val screenWidth: Int, private val screenHeight: Int) {
    var absoluteX = 0
    var absoluteY = 0
    var isDragging = false
    private var dragOffsetX = 0
    private var dragOffsetY = 0
    var isDefault = true

    fun draw() {
        absoluteX = (x * screenWidth).toInt()
        absoluteY = (y * screenHeight).toInt()
        val color: Int = if (isDragging) -0x7f000001 else 0
        Gui.drawRect(absoluteX, absoluteY, absoluteX + width, absoluteY + height, color)
        when (contents) {
            0 -> {
                val widthDirector = fr.getStringWidth("➤ ")
                fr.drawStringWithShadow("§5➤ ", absoluteX.toFloat(), absoluteY.toFloat(), 0)
                fr.drawStringWithShadow("§a${ZAConfig.waveDelays.textStyle}", (absoluteX + widthDirector).toFloat(), absoluteY.toFloat(), 0)
                fr.drawStringWithShadow("§e${ZAConfig.waveDelays.textStyle}", (absoluteX + widthDirector).toFloat(), (absoluteY + fr.FONT_HEIGHT).toFloat(), 0)
                for (i in 2..7) fr.drawStringWithShadow("§8${ZAConfig.waveDelays.textStyle}", (absoluteX + widthDirector).toFloat(), (absoluteY + fr.FONT_HEIGHT * i).toFloat(), 0)
            }
            1 -> {
                fr.drawStringWithShadow("§cInsta Kill: §fRound 24", (absoluteX + (if (absoluteX + width / 2 >= getX() / 2) width - fr.getStringWidth("Insta Kill: Round 24") else 0)).toFloat(), absoluteY.toFloat(), 0)
                fr.drawStringWithShadow("§9Max Ammo: §fRound 102", (absoluteX + (if (absoluteX + width / 2 >= getX() / 2) width - fr.getStringWidth("Max Ammo: Round 102") else 0)).toFloat(), (absoluteY + fr.FONT_HEIGHT).toFloat(), 0)
                fr.drawStringWithShadow("§5Shopping Spree: §fRound 105", absoluteX.toFloat(), (absoluteY + fr.FONT_HEIGHT * 2).toFloat(), 0)
                fr.drawStringWithShadow("§6Double Gold: §f60s", (absoluteX + (if (absoluteX + width / 2 >= getX() / 2) width - fr.getStringWidth("Double Gold: 60s") else 0)).toFloat(), (absoluteY + fr.FONT_HEIGHT * 3).toFloat(), 0)
                fr.drawStringWithShadow("§9Carpenter: §f60s", (absoluteX + (if (absoluteX + width / 2 >= getX() / 2) width - fr.getStringWidth("Carpenter: 60s") else 0)).toFloat(), (absoluteY + fr.FONT_HEIGHT * 4).toFloat(), 0)
                fr.drawStringWithShadow("§6Bonus Gold: §f60s", (absoluteX + (if (absoluteX + width / 2 >= getX() / 2) width - fr.getStringWidth("Bonus Gold: 60s") else 0)).toFloat(), (absoluteY + fr.FONT_HEIGHT * 5).toFloat(), 0)
            }
            2 -> fr.drawStringWithShadow("0:00.0", absoluteX.toFloat(), absoluteY.toFloat(), 0xFFFFFF)
            3 -> fr.drawStringWithShadow("$NAME v$VERSION", absoluteX.toFloat(), absoluteY.toFloat(), 0xffff55)
            4 -> {
                fr.drawStringWithShadow("Player Visibility: §coff", absoluteX.toFloat(), absoluteY.toFloat(), 0xffff55)
                fr.drawStringWithShadow("Block Alarm: §coff", (absoluteX + (if (absoluteX + width / 2 >= getX() / 2) width - fr.getStringWidth("Block Alarm: §coff") else 0)).toFloat(), (absoluteY + fr.FONT_HEIGHT).toFloat(), 0xffff55)
                fr.drawStringWithShadow("Auto Rejoin: §coff", (absoluteX + (if (absoluteX + width / 2 >= getX() / 2) width - fr.getStringWidth("Auto Rejoin: §coff") else 0)).toFloat(), (absoluteY + fr.FONT_HEIGHT * 2).toFloat(), 0xffff55)
            }
        }
    }

    fun isMouseOver(mouseX: Int, mouseY: Int) = mouseX in absoluteX..absoluteX + width && mouseY in absoluteY..absoluteY + height

    fun onMousePressed(mouseX: Int, mouseY: Int) {
        if (!isMouseOver(mouseX, mouseY)) return
        isDefault = false
        isDragging = true
        dragOffsetX = absoluteX - mouseX
        dragOffsetY = absoluteY - mouseY
    }

    fun onMouseReleased() {
        isDragging = false
    }

    fun onMouseDragged(mouseX: Int, mouseY: Int) {
        if (!isDragging) return
        absoluteX = mouseX + dragOffsetX
        absoluteY = mouseY + dragOffsetY
        x = absoluteX.toDouble() / screenWidth.toDouble()
        y = absoluteY.toDouble() / screenHeight.toDouble()

        if (x < 0) {
            absoluteX = 0
            x = 0.0
        } else if (absoluteX + width > screenWidth) {
            absoluteX = screenWidth - width
            x = (absoluteX).toDouble() / screenWidth.toDouble()
        }

        if (y < 0) {
            absoluteY = 0
            y = 0.0
        } else if (absoluteY + height > screenHeight) {
            absoluteY = screenHeight - height
            y = (absoluteY).toDouble() / screenHeight.toDouble()
        }
    }
}