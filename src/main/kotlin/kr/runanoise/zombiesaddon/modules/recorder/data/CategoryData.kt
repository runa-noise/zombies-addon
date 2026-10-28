package kr.runanoise.zombiesaddon.modules.recorder.data

import com.google.gson.Gson
import kr.runanoise.zombiesaddon.enums.ZombiesMap
import kr.runanoise.zombiesaddon.modules.recorder.ISplitData

class CategoryData(map: ZombiesMap): ISplitData {
    val bestSegments: ShortArray
    val personalBests: IntArray

    init {
        when (map) {
            ZombiesMap.DEAD_END, ZombiesMap.BAD_BLOOD -> {
                bestSegments = ShortArray(30) { 0 }
                personalBests = IntArray(30) { 0 }
            }
            ZombiesMap.ALIEN_ARCADIUM -> {
                bestSegments = ShortArray(105) { 0 }
                personalBests = IntArray(105) { 0 }
            }
            ZombiesMap.PRISON -> {
                bestSegments = ShortArray(30) { 0 }
                personalBests = IntArray(31) { 0 }
            }
        }
    }

    override fun toJson() = Gson().toJson(this, CategoryData::class.java)!!
}