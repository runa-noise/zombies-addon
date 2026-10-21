package kr.hahaha98757.zombiesaddon.config.categories

import net.minecraftforge.common.config.ConfigElement
import net.minecraftforge.common.config.Configuration
import net.minecraftforge.common.config.Property
import net.minecraftforge.fml.client.config.DummyConfigElement
import net.minecraftforge.fml.client.config.IConfigElement

abstract class AbstractCategory {
    private val options = mutableMapOf<String, IConfigElement>()

    operator fun get(name: String) = options[name]
    operator fun set(name: String, element: IConfigElement) {
        options[name] = element
    }

    abstract val categoryName: String
    abstract val categoryKey: String
    abstract fun sync(config: Configuration)

    protected fun addOption(prop: Property): Property {
        prop.languageKey = "zombiesaddon.config.$categoryKey.${prop.name}"
        prop.comment = "zombiesaddon.config.$categoryKey.${prop.name}.comment"
        this[prop.name] = ConfigElement(prop)
        return prop
    }

    fun getCategoryElement() = DummyConfigElement.DummyCategoryElement(
        categoryName,
        "zombiesaddon.config.category.$categoryKey",
        options.values.toList()
    )
}