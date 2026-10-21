package kr.hahaha98757.zombiesaddon.modules

import com.seosean.showspawntime.config.MainConfiguration
import kr.hahaha98757.zombiesaddon.KeyBindings
import kr.hahaha98757.zombiesaddon.ZombiesAddon
import kr.hahaha98757.zombiesaddon.config.ZAConfig
import kr.hahaha98757.zombiesaddon.enums.SemiPvMode
import kr.hahaha98757.zombiesaddon.utils.addTranslatedChat
import kr.hahaha98757.zombiesaddon.utils.isNotPlayZombies
import kr.hahaha98757.zombiesaddon.utils.mc
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.RenderHelper
import net.minecraft.entity.player.EntityPlayer
import net.minecraftforge.client.event.RenderWorldLastEvent
import net.minecraftforge.fml.common.eventhandler.Event
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL14
import kotlin.math.sqrt

object PlayerVisibility: ToggleableModule("Player Visibility", ZAConfig.pv.default) {
    override fun getKeyBinding() = KeyBindings.togglePv
    override fun addToggleText(enabled: Boolean) =
        addTranslatedChat("zombiesaddon.modules.general.toggled", "Player Visibility", if (enabled) "§aon" else "§coff")

    var pass = 0
    var inLast = false

    fun modeOf(player: EntityPlayer): Mode {
        if (!isEnable() || isNotPlayZombies()) return Mode.NONE
        if (player == mc.thePlayer || player.isPlayerSleeping) return Mode.NONE

        val distance = mc.thePlayer.getDistanceToEntity(player)
        if (distance <= ZAConfig.pv.range) return Mode.PV
        if (!ZAConfig.pv.enableSemiPv) return Mode.NONE
        if (distance > ZAConfig.pv.semiPvRange) return Mode.NONE
        if (player.isInvisibleToPlayer(mc.thePlayer)) return Mode.NONE
        return Mode.SEMI_PV
    }

    fun applySemiPv(player: EntityPlayer) {
        if (pass != 2) return
        GlStateManager.enableBlend()
        GL14.glBlendColor(0f, 0f, 0f, getAlpha(player))
        GlStateManager.blendFunc(GL11.GL_CONSTANT_ALPHA, GL11.GL_ONE_MINUS_CONSTANT_ALPHA)
        if (ZAConfig.pv.fastSemiPv) GlStateManager.depthMask(false)
        else GlStateManager.depthFunc(GL11.GL_EQUAL)
    }

    fun restoreSemiPv() {
        if (pass != 2) return
        GlStateManager.depthMask(true)
        GlStateManager.depthFunc(GL11.GL_LEQUAL)
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
        GlStateManager.disableBlend()
    }

    private fun onRenderLast(event: RenderWorldLastEvent) {
        val world = mc.theWorld ?: return
        val cam = mc.renderViewEntity ?: return

        val semiPvPlayers = world.playerEntities.filter { modeOf(it) == Mode.SEMI_PV }
            .sortedByDescending { cam.getDistanceSqToEntity(it) }
        if (semiPvPlayers.isEmpty()) return

        RenderHelper.enableStandardItemLighting()
        mc.entityRenderer.enableLightmap()

        var sstValue: Boolean? = null
        if (ZombiesAddon.instance.hasSST) runCatching {
            sstValue = MainConfiguration.PlayerInvisible
            MainConfiguration.PlayerInvisible = false
        }
        inLast = true
        try {
            semiPvPlayers.forEach { mc.renderManager.renderEntitySimple(it, event.partialTicks) }
        } finally {
            inLast = false
            mc.entityRenderer.disableLightmap()
            RenderHelper.disableStandardItemLighting()
            if (ZombiesAddon.instance.hasSST && sstValue != null)
                runCatching { MainConfiguration.PlayerInvisible = sstValue }
        }
    }

    fun getAlpha(player: EntityPlayer): Float {
        val mode = ZAConfig.pv.semiPvMode
        if (mode == SemiPvMode.FIXED)
            return ZAConfig.pv.semiPvMinAlpha.toFloat()

        val distance = mc.thePlayer.getDistanceToEntity(player)
        val pvRange = ZAConfig.pv.range
        val semiPvRange = ZAConfig.pv.semiPvRange
        val minAlpha = ZAConfig.pv.semiPvMinAlpha
        val maxAlpha = ZAConfig.pv.semiPvMaxAlpha

        val ratio = ((pvRange - distance) / (pvRange - semiPvRange)).coerceIn(0.0, 1.0)
        val factor = when (mode) {
            SemiPvMode.LINEAR -> ratio
            SemiPvMode.SMOOTH -> ratio * ratio
            SemiPvMode.SHARP -> sqrt(ratio)
            SemiPvMode.FIXED -> return minAlpha.toFloat() // 도달할 수 없는 구간
        }

        return (minAlpha + (maxAlpha - minAlpha) * factor).toFloat()
    }

    override fun onEvent(event: Event) {
        if (event is RenderWorldLastEvent) onRenderLast(event)
    }

    enum class Mode {
        NONE, SEMI_PV, PV
    }
}