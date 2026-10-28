package kr.runanoise.zombiesaddon.enums

import kr.runanoise.zombiesaddon.data.Round
import kr.runanoise.zombiesaddon.utils.JsonLoader

enum class GameMode(val map: ZombiesMap, val difficulty: Difficulty, val rounds: Array<Round>) {
    DEAD_END_NORMAL(ZombiesMap.DEAD_END, Difficulty.NORMAL, readFromFile("data/rounds/DEN_WAVES.json")),
    DEAD_END_HARD(ZombiesMap.DEAD_END, Difficulty.HARD, readFromFile("data/rounds/DEH_WAVES.json")),
    DEAD_END_RIP(ZombiesMap.DEAD_END, Difficulty.RIP, readFromFile("data/rounds/DER_WAVES.json")),

    BAD_BLOOD_NORMAL(ZombiesMap.BAD_BLOOD, Difficulty.NORMAL, readFromFile("data/rounds/BBN_WAVES.json")),
    BAD_BLOOD_HARD(ZombiesMap.BAD_BLOOD, Difficulty.HARD, readFromFile("data/rounds/BBH_WAVES.json")),
    BAD_BLOOD_RIP(ZombiesMap.BAD_BLOOD, Difficulty.RIP, readFromFile("data/rounds/BBR_WAVES.json")),

    ALIEN_ARCADIUM(ZombiesMap.ALIEN_ARCADIUM, Difficulty.NORMAL, readFromFile("data/rounds/AA_WAVES.json")),

    PRISON_NORMAL(ZombiesMap.PRISON, Difficulty.NORMAL, readFromFile("data/rounds/PRN_WAVES.json")),
    PRISON_HARD(ZombiesMap.PRISON, Difficulty.HARD, readFromFile("data/rounds/PRH_WAVES.json")),
    PRISON_RIP(ZombiesMap.PRISON, Difficulty.RIP, readFromFile("data/rounds/PRR_WAVES.json"));

    fun appliedDifficulty(difficulty: Difficulty) = when (map) {
        ZombiesMap.DEAD_END -> when (difficulty) {
            Difficulty.NORMAL -> DEAD_END_NORMAL
            Difficulty.HARD -> DEAD_END_HARD
            Difficulty.RIP -> DEAD_END_RIP
        }
        ZombiesMap.BAD_BLOOD -> when (difficulty) {
            Difficulty.NORMAL -> BAD_BLOOD_NORMAL
            Difficulty.HARD -> BAD_BLOOD_HARD
            Difficulty.RIP -> BAD_BLOOD_RIP
        }
        ZombiesMap.ALIEN_ARCADIUM -> ALIEN_ARCADIUM
        ZombiesMap.PRISON -> when (difficulty) {
            Difficulty.NORMAL -> PRISON_NORMAL
            Difficulty.HARD -> PRISON_HARD
            Difficulty.RIP -> PRISON_RIP
        }
    }

    override fun toString() = when (this) {
        DEAD_END_NORMAL -> "Dead End Normal"
        DEAD_END_HARD -> "Dead End Hard"
        DEAD_END_RIP -> "Dead End RIP"
        BAD_BLOOD_NORMAL -> "Bad Blood Normal"
        BAD_BLOOD_HARD -> "Bad Blood Hard"
        BAD_BLOOD_RIP -> "Bad Blood RIP"
        ALIEN_ARCADIUM -> "Alien Arcadium"
        PRISON_NORMAL -> "Prison Normal"
        PRISON_HARD -> "Prison Hard"
        PRISON_RIP -> "Prison RIP"
    }

    fun toStringForRecorder() = when (this) {
        DEAD_END_NORMAL -> "DEAD_END"
        DEAD_END_HARD -> "DEAD_END_HARD"
        DEAD_END_RIP -> "DEAD_END_RIP"
        BAD_BLOOD_NORMAL -> "BAD_BLOOD"
        BAD_BLOOD_HARD -> "BAD_BLOOD_HARD"
        BAD_BLOOD_RIP -> "BAD_BLOOD_RIP"
        ALIEN_ARCADIUM -> "ALIEN_ARCADIUM"
        PRISON_NORMAL -> "PRISON"
        PRISON_HARD -> "PRISON_HARD"
        PRISON_RIP -> "PRISON_RIP"
    }
}

private fun readFromFile(resourcePath: String): Array<Round> =
    JsonLoader.loadJsonFromResource(resourcePath, Array<Round>::class.java)