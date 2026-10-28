package kr.runanoise.zombiesaddon.modules

import kr.runanoise.zombiesaddon.KeyBindings
import kr.runanoise.zombiesaddon.utils.fr
import kr.runanoise.zombiesaddon.utils.isDisable
import net.minecraft.client.gui.Gui
import net.minecraftforge.client.event.RenderGameOverlayEvent
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent
import org.apache.commons.lang3.text.WordUtils


object ZombiesStratViewer: Module("Zombies Strat Viewer") {
    var enabled = false
    val stratLines = mutableListOf<String>()
    var currentLine = 0
    var linesOfView = 1
    private val actualLines = mutableListOf<String>()
    private var width = 0
    private var height = 20

    fun refreshActualLines() {
        height = 0
        width = 20
        actualLines.clear()
        for (i in currentLine..<currentLine + linesOfView) {
            if (i >= stratLines.size) continue
            var line = stratLines[i]
            if (line.isEmpty()) {
                actualLines += $$"$CONTROL EMPTY$"
                height += 10
            } else {
                line = WordUtils.wrap(line, 55)
                val strArr = line.split("\n")
                for (s in strArr) {
                    val str = s.replace("\r", "")
                    actualLines += str
                    val width = fr.getStringWidth(str)
                    if (width > this.width) this.width = width
                    height += 10
                }
                actualLines += $$"$HEY CONTROL$"
                height += 5
            }
        }
    }

    override fun onKeyInput(event: KeyInputEvent) {
        if (KeyBindings.zsvScrollDown.isPressed) {
            if (isDisable() || !isEnable()) return
            if (currentLine + 1 < stratLines.size) currentLine++
            refreshActualLines()
        }
        if (KeyBindings.zsvScrollUp.isPressed) {
            if (isDisable() || !isEnable()) return
            if (currentLine > 0) currentLine--
            refreshActualLines()
        }
    }

    override fun onRender(event: RenderGameOverlayEvent.Text) {
        Gui.drawRect(3, 3, 5 + width, 5 + height, 0x55555555)
        var y = -5

        for (str in actualLines)
            when (str) {
                $$"$HEY CONTROL$" -> y += 5
                $$"$CONTROL EMPTY$" -> y += 10
                else -> {
                    y += 10
                    fr.drawStringWithShadow(str, 5f, y.toFloat(), 0xffffff)
                }
            }
    }

    override fun isEnable() = enabled
}