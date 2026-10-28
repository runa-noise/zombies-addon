package kr.runanoise.zombiesaddon.utils

import com.google.gson.Gson
import com.google.gson.JsonParser
import kr.runanoise.zombiesaddon.MODID
import net.minecraft.util.ResourceLocation
import java.io.File
import java.io.FileReader
import java.io.InputStreamReader

object JsonLoader {
    fun <T> loadJsonFromFile(file: File, clazz: Class<T>): T =
        FileReader(file).use { return Gson().fromJson(JsonParser().parse(it), clazz) }

    fun <T> loadJsonFromResource(path: String, clazz: Class<T>): T {
        val resource = ResourceLocation(MODID, path)
        InputStreamReader(mc.resourceManager.getResource(resource).inputStream).use { return Gson().fromJson(JsonParser().parse(it), clazz) }
    }
}