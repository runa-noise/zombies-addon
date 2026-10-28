package kr.runanoise.zombiesaddon.enums

enum class ZombiesMap {
    DEAD_END, BAD_BLOOD, ALIEN_ARCADIUM, PRISON;

    fun getNormalGameMode() = when (this) {
        DEAD_END -> GameMode.DEAD_END_NORMAL
        BAD_BLOOD -> GameMode.BAD_BLOOD_NORMAL
        ALIEN_ARCADIUM -> GameMode.ALIEN_ARCADIUM
        PRISON -> GameMode.PRISON_NORMAL
    }

    override fun toString(): String {
        return when (this) {
            DEAD_END -> "Dead End"
            BAD_BLOOD -> "Bad Blood"
            ALIEN_ARCADIUM -> "Alien Arcadium"
            PRISON -> "Prison"
        }
    }
}

enum class Difficulty {
    NORMAL, HARD, RIP
}

enum class Status {
    SURVIVE, REVIVE, DEAD, QUIT
}

enum class Language(val text: String) {
    AUTO("Auto"),
    KO_KR("한국어 (한국)"),
    EN_US("English (US)"),
    JA_JP("日本語 (日本)");

    override fun toString() = text

    companion object {
        fun fromText(text: String) = entries.firstOrNull { it.text == text } ?: AUTO
        val arrays get() = entries.map { it.text }.toTypedArray()
    }
}

enum class SemiPvMode(val text: String) {
    LINEAR("Linear"), FIXED("Fixed"), SMOOTH("Smooth"), SHARP("Sharp");

    override fun toString() = text

    companion object {
        fun fromText(text: String) = entries.firstOrNull { it.text == text } ?: LINEAR
        val arrays get() = entries.map { it.text }.toTypedArray()
    }
}

enum class ITTextStyle(val text: String) {
    ZOMBIES_ADDON("0:10.0"), DETAILS("0:10.00"), SST("00:10");

    override fun toString() = text

    companion object {
        fun fromText(text: String) = entries.firstOrNull { it.text == text } ?: ZOMBIES_ADDON
        val arrays get() = entries.map { it.text }.toTypedArray()
    }
}


enum class ITMode(val text: String) {
    SERVER("Server"), CLIENT("Client");

    override fun toString() = text

    companion object {
        fun fromText(text: String) = entries.firstOrNull { it.text == text } ?: SERVER
        val arrays get() = entries.map { it.text }.toTypedArray()
    }
}
enum class WDTextStyle(val text: String) {

    ZOMBIES_ADDON("W1: 0:10.0"), DETAILS("W1: 0:10.00"), SST("W1 10:00");

    override fun toString() = text

    companion object {
        fun fromText(text: String) = entries.firstOrNull { it.text == text } ?: ZOMBIES_ADDON
        val arrays get() = entries.map { it.text }.toTypedArray()
    }
}

enum class WDHighlightStyle(val text: String) {

    ZOMBIES_ADDON("Zombies Addon"), ZOMBIES_UTILS("Zombies Utils");

    override fun toString() = text

    companion object {
        fun fromText(text: String) = entries.firstOrNull { it.text == text } ?: ZOMBIES_ADDON
        val arrays get() = entries.map { it.text }.toTypedArray()
    }
}

enum class WDOffsetMode {
    OFF, RL_MODE, WRATH_MODE
}