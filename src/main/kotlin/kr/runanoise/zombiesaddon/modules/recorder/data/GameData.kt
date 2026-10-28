package kr.runanoise.zombiesaddon.modules.recorder.data

import com.google.gson.Gson
import kr.runanoise.zombiesaddon.enums.ZombiesMap
import kr.runanoise.zombiesaddon.modules.recorder.ISplitData

class GameData(map: ZombiesMap): ISplitData {
    val segments = when (map) {
        ZombiesMap.DEAD_END, ZombiesMap.BAD_BLOOD -> ShortArray(30) { 0 }
        ZombiesMap.ALIEN_ARCADIUM -> ShortArray(105) { 0 }
        ZombiesMap.PRISON -> ShortArray(31) { 0 }
    }

    override fun toJson() = Gson().toJson(this, GameData::class.java)!!
}