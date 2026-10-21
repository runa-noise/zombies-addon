package kr.hahaha98757.zombiesaddon.config

import kr.hahaha98757.zombiesaddon.MODID
import kr.hahaha98757.zombiesaddon.NAME
import kr.hahaha98757.zombiesaddon.utils.getTranslatedString
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiScreen
import net.minecraftforge.fml.client.IModGuiFactory
import net.minecraftforge.fml.client.config.GuiConfig

@Suppress("unused")
class ZAGuiFactory: IModGuiFactory {
    override fun initialize(minecraft: Minecraft?) = Unit
    override fun mainConfigGuiClass() = ZAGuiConfig::class.java
    override fun runtimeGuiCategories() = null
    override fun getHandlerFor(runtimeOptionCategoryElement: IModGuiFactory.RuntimeOptionCategoryElement?) = null
}
class ZAGuiConfig(parentScreen: GuiScreen): GuiConfig(parentScreen, ZAConfig.getElements(), MODID, false, false, "$NAME ${getTranslatedString("zombiesaddon.config.title")}")