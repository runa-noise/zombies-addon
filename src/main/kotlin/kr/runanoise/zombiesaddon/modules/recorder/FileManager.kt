package kr.runanoise.zombiesaddon.modules.recorder

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import kr.runanoise.zombiesaddon.modules.recorder.data.CategoryData
import kr.runanoise.zombiesaddon.modules.recorder.files.CategoryFile
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException

fun CategoryFile.readOrCreate() = runCatching { readData() }.getOrElse {
    val data = CategoryData(gameMode.map)
    createData(data)
    data
}

fun File.createData(data: ISplitData) {
    parentFile.mkdirs()
    createNewFile()
    writeData(data)
}

@Throws(IOException::class)
fun File.writeData(data: ISplitData) = FileUtils.writeStringToFile(this, data.toJson(), Charsets.UTF_16)

private fun File.readData(): CategoryData {
    if (!exists()) throw FileNotFoundException()

    val json = FileUtils.readFileToString(this, Charsets.UTF_16)

    if (json == null || json.isEmpty()) throw JsonSyntaxException("File empty")
    return Gson().fromJson(json, CategoryData::class.java)
}