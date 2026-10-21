package kr.hahaha98757.zombiesaddon.modules

import kr.hahaha98757.zombiesaddon.config.ZAConfig
import kr.hahaha98757.zombiesaddon.data.Room
import kr.hahaha98757.zombiesaddon.enums.ZombiesMap
import kr.hahaha98757.zombiesaddon.events.GameEndEvent
import kr.hahaha98757.zombiesaddon.events.RoundStartEvent
import kr.hahaha98757.zombiesaddon.utils.JsonLoader
import kr.hahaha98757.zombiesaddon.utils.addTranslatedChat
import kr.hahaha98757.zombiesaddon.utils.fr
import kr.hahaha98757.zombiesaddon.utils.mc
import net.minecraftforge.client.event.RenderGameOverlayEvent
import kotlin.math.pow

object SlaHandler: AlwaysEnableModule("Spawn Limit Action") {
    override fun onRender(event: RenderGameOverlayEvent.Text) {
        Sla.sla?.refresh() ?: return
        val rooms = Sla.sla?.rooms ?: return
        var y = 0
        for (room in rooms) {
            if (!ZAConfig.sla.showInactiveWindows && room.activeWindows == 0) continue
            fr.drawStringWithShadow(room.toString(), 1f, 1f + fr.FONT_HEIGHT * y++, 0xffffff)
        }
    }

    override fun onRoundStart(event: RoundStartEvent) {
        if (!ZAConfig.sla.autoSla) return
        if (event.game.round != 1) return
        val map = event.game.gameMode.map
        runCatching { Sla.on(map) }
    }

    override fun onGameEnd(event: GameEndEvent) {
        if (!ZAConfig.sla.autoSla) return
        Sla.off()
    }
}

class Sla(map: ZombiesMap) {
    companion object {
        var sla: Sla? = null
            private set

        fun off() {
            sla = null
            addTranslatedChat("zombiesaddon.commands.sla.setMap", "§coff")
        }

        fun on(map: ZombiesMap) {
            sla = Sla(map)
            addTranslatedChat("zombiesaddon.commands.sla.setMap", "§a$map")
        }
    }

    val rooms = when (map) {
        ZombiesMap.DEAD_END -> JsonLoader.loadJsonFromResource("data/sla/DE.json", Array<Room>::class.java)
        ZombiesMap.BAD_BLOOD -> JsonLoader.loadJsonFromResource("data/sla/BB.json", Array<Room>::class.java)
        ZombiesMap.ALIEN_ARCADIUM -> JsonLoader.loadJsonFromResource("data/sla/AA.json", Array<Room>::class.java)
        else -> throw IllegalArgumentException("맵 ${map}은(는) SLA를 지원하지 않습니다.")
    }
    private val offset = IntArray(3)
    private var rotations = 0
    private var mirroring = booleanArrayOf(false, false)

    fun resetRotate() {
        rotate(4 - rotations, true)
        addTranslatedChat("zombiesaddon.commands.sla.resetRotations")
    }

    fun rotate(rotations: Int, reset: Boolean = false) {
        var newRotations = rotations % 4
        if (newRotations < 0) newRotations += 4
        sla?.run {
            this.rotations = (this.rotations + newRotations) % 4
            for (room in rooms) for (window in room.windows) window.rotate(newRotations)
            if (reset) return
            addTranslatedChat("zombiesaddon.commands.sla.rotates", newRotations * 90)
        }
    }

    fun resetMirroring() {
        if (mirroring[0]) mirrorX(true)
        if (mirroring[1]) mirrorZ(true)
        addTranslatedChat("zombiesaddon.commands.sla.resetMirroring")
    }

    fun mirrorX(reset: Boolean = false) {
        mirroring[0] = !mirroring[0]
        for (room in rooms) for (window in room.windows) window.mirrorX()
        if (reset) return
        addTranslatedChat("zombiesaddon.commands.sla.mirror", "0 y z")
    }

    fun mirrorZ(reset: Boolean = false) {
        mirroring[1] = !mirroring[1]
        for (room in rooms) for (window in room.windows) window.mirrorZ()
        if (reset) return
        addTranslatedChat("zombiesaddon.commands.sla.mirror", "0 x z")
    }

    fun resetOffset() = setOffset(0, 0, 0)

    fun setOffset(x: Int, y: Int, z: Int) {
        offset[0] = x
        offset[1] = y
        offset[2] = z
        addTranslatedChat("zombiesaddon.commands.sla.setOffset", "$x $y $z")
    }

    fun refresh() {
        val player = mc.thePlayer ?: return
        val playerCoords = doubleArrayOf((player.posX - offset[0]), (player.posY - offset[1]), (player.posZ - offset[2]))
        for (room in rooms) {
            room.activeWindows = 0
            for (window in room.windows) {
                /*
                창문의 좌표는 정수로 표기하기 위해 2배하여 저장되어 있음.
                따라서 플레이어의 좌표도 2배하여 비교해야 함.
                distance는 (2x)^2 + (2y)^2 + (2z)^2 = 4(x^2 + y^2 + z^2) 이다.
                SLA 범위는 50m이므로 4*50^2 = 10000이다.
                이 둘을 비교하여 SLA 범위에 있는지 확인한다.
                 */
                var distance = 0.0
                for (i in 0..2) distance += (playerCoords[i]*2 - window.xyz[i]).pow(2)
                if (distance < 10000) {
                    window.active = true
                    room.activeWindows++
                } else window.active = false
            }
        }
    }
}