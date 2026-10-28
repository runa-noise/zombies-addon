package kr.runanoise.zombiesaddon.update

import kr.runanoise.zombiesaddon.update.VersionType.*

data class Version(val x: Int, val y: Int, val z: Int, val versionType: VersionType, val w: Int): Comparable<Version> {
    constructor(x: Int, y: Int, z: Int): this(x, y, z, RELEASE, 9999)
    private constructor(): this(0, 0, 0)

    companion object {
        val ZERO = Version()

        fun fromString(str: String): Version {
            try {
                if ("-" !in str) {
                    val strArray = str.split(".")
                    return Version(strArray[0].toInt(), strArray[1].toInt(), strArray[2].toInt())
                }
                val versionType = when {
                    "alpha" in str -> ALPHA
                    "beta" in str -> BETA
                    "pre" in str -> PRE_RELEASE
                    "rc" in str -> RELEASE_CANDIDATE
                    else -> RELEASE
                }
                val w = str.split("-")[1].replace(Regex("[^0-9]"), "").toInt()
                val strArray = str.split("-")[0].split(".")
                return Version(strArray[0].toInt(), strArray[1].toInt(), strArray[2].toInt(), versionType, w)
            } catch (e: Exception) {
                throw VersionParseException(str, e)
            }
        }
    }

    override fun toString() = if (versionType == RELEASE) "$x.$y.$z"
    else "$x.$y.$z-${versionType.str}$w"

    override fun compareTo(other: Version) = when {
        this.x > other.x -> 1
        this.x < other.x -> -1
        this.y > other.y -> 1
        this.y < other.y -> -1
        this.z > other.z -> 1
        this.z < other.z -> -1
        this.versionType > other.versionType -> 1
        this.versionType < other.versionType -> -1
        this.w > other.w -> 1
        this.w < other.w -> -1
        else -> 0
    }
}

enum class VersionType(val str: String) {
    ALPHA("alpha"), BETA("beta"), PRE_RELEASE("pre"), RELEASE_CANDIDATE("rc"), RELEASE("");
}

class VersionParseException(message: String, cause: Throwable?): Exception(message, cause)