package kr.runanoise.zombiesaddon.modules

import kr.runanoise.zombiesaddon.MODID
import kr.runanoise.zombiesaddon.config.ZAConfig
import kr.runanoise.zombiesaddon.game.GameManager
import kr.runanoise.zombiesaddon.utils.*
import net.minecraft.client.gui.Gui
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.init.Items
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.event.RenderGameOverlayEvent
import net.minecraftforge.fml.common.eventhandler.Event

object LastWeapons: Module("Last Weapons") {
    private val weapons = arrayOfNulls<ItemStack>(9)
    private val armors = arrayOfNulls<ItemStack>(4)

    val isWork: Boolean get() {
        if (!isEnable()) return false
        val game = GameManager.game ?: return false
        if (!game.gameEnd) return false
        if (!game.isWin && !ZAConfig.lw.workInGameOver) return false
        return true
    }

    override fun onEvent(event: Event) {
        if (event !is RenderGameOverlayEvent.Post) return
        if (event.type != RenderGameOverlayEvent.ElementType.HOTBAR) return

        if (isWork) {
            val renderItem = mc.renderItem
            var x = (getX() / 2 - 88).toInt()
            var y = (getY() - 19).toInt()

            GlStateManager.pushAttrib()

            for (i in 0..8) {
                val weapon = weapons[i]

                weapon?.let {
                    if (i == 4 && ZAConfig.lw.displayCooledDownSkill && it.item == Items.dye && it.itemDamage == 8) {
                        val name = it.displayName.withoutColor()
                        val replaced = when (name) {
                            in heal -> displayTexture("textures/items/heal_cool.png", x + 20 * i, y)
                            in lrod -> displayTexture("textures/items/lrod_cool.png", x + 20 * i, y)
                            in turret -> displayTexture("textures/items/turret_cool.png", x + 20 * i, y)
                            else -> false
                        }
                        if (replaced) {
                            renderItem.renderItemOverlayIntoGUI(fr, it, x + 20 * i, y, null)
                            return@let
                        }
                    }

                    val level = getLevel(it.displayName.withoutColor())

                    renderItem.renderItemAndEffectIntoGUI(it, x + 20 * i, y)
                    if (ZAConfig.lw.displayLevel && level != 0)
                        displayTexture("textures/items/level$level.png", x + 20 * i, y)
                    renderItem.renderItemOverlayIntoGUI(fr, it, x + 20 * i, y, null)
                }
            }

            GlStateManager.popAttrib()

            if (ZAConfig.lw.displayArmors) {
                x = (getX() / 2 + 12).toInt()
                y = (getY() - 60).toInt()

                for (i in 0..3) {
                    renderItem.renderItemAndEffectIntoGUI(armors[3-i], x + 20 * i, y)
                    renderItem.renderItemOverlayIntoGUI(fr, armors[3-i], x + 20 * i, y, null)
                }
            }
        }

        val player = mc.thePlayer ?: return

        if (player.inventory.getStackInSlot(1) == null) return

        for (i in 0..8) weapons[i] = player.inventory.getStackInSlot(i)
        player.inventory.armorInventory.copyInto(armors)
    }

    private fun displayTexture(path: String, x: Int, y: Int): Boolean {
        mc.textureManager.bindTexture(ResourceLocation(MODID, path))
        GlStateManager.pushMatrix()
        GlStateManager.disableDepth()
        GlStateManager.enableBlend()
        GlStateManager.color(1f, 1f, 1f, 1f)

        Gui.drawModalRectWithCustomSizedTexture(x, y, 0f, 0f, 16, 16, 16f, 16f)

        GlStateManager.disableBlend()
        GlStateManager.enableDepth()
        GlStateManager.popMatrix()
        return true
    }

    override fun isEnable() = ZAConfig.lw.enable
}