package kr.hahaha98757.zombiesaddon.modules.recorder

import kr.hahaha98757.zombiesaddon.config.ZAConfig
import kr.hahaha98757.zombiesaddon.enums.GameMode
import kr.hahaha98757.zombiesaddon.modules.recorder.files.CategoryFile
import kr.hahaha98757.zombiesaddon.utils.isHypixel
import java.io.File

class Category {
    private val categoryFiles: Array<CategoryFile>
    val name = selectedCategory

    init {
        val category = if (isHypixel()) File("zombies/splits", selectedCategory)
        else File("zombies/practise-splits", selectedCategory)
        categoryFiles = arrayOf(
            CategoryFile(category, GameMode.DEAD_END_NORMAL),
            CategoryFile(category, GameMode.DEAD_END_HARD),
            CategoryFile(category, GameMode.DEAD_END_RIP),

            CategoryFile(category, GameMode.BAD_BLOOD_NORMAL),
            CategoryFile(category, GameMode.BAD_BLOOD_HARD),
            CategoryFile(category, GameMode.BAD_BLOOD_RIP),

            CategoryFile(category, GameMode.ALIEN_ARCADIUM),

            CategoryFile(category, GameMode.PRISON_NORMAL),
            CategoryFile(category, GameMode.PRISON_HARD),
            CategoryFile(category, GameMode.PRISON_RIP)
        )
    }

    fun getByGameMode(gameMode: GameMode) = categoryFiles.first { it.gameMode == gameMode }

    companion object {
        var selectedCategory = ZAConfig.recorder.defaultCategory
    }
}