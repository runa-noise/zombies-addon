package kr.hahaha98757.zombiesaddon.utils

import kr.hahaha98757.zombiesaddon.MODID
import kr.hahaha98757.zombiesaddon.ZombiesAddon
import kr.hahaha98757.zombiesaddon.commands.CommandZaDebug
import kr.hahaha98757.zombiesaddon.config.ZAConfig
import kr.hahaha98757.zombiesaddon.data.ServerNumber
import kr.hahaha98757.zombiesaddon.enums.Difficulty
import kr.hahaha98757.zombiesaddon.enums.Language
import kr.hahaha98757.zombiesaddon.enums.Status
import kr.hahaha98757.zombiesaddon.enums.ZombiesMap
import kr.hahaha98757.zombiesaddon.game.GameManager
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.ScaledResolution
import net.minecraft.client.resources.I18n
import net.minecraft.scoreboard.ScorePlayerTeam
import net.minecraft.util.*
import net.minecraftforge.fml.common.Loader
import java.io.File
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import java.util.*

// private 필드
private val serverNumberPattern = Regex("\\d{2}/\\d{2}/\\d{2}\\s+([mLM][0-9A-Z]+|#\\d+)")
private val emojiPattern = Regex("[\uD83D\uDD2B\uD83C\uDF6B\uD83D\uDCA3\uD83D\uDC7D\uD83D\uDD2E\uD83D\uDC0D\uD83D\uDC7E\uD83C\uDF20\uD83C\uDF6D\u26BD\uD83C\uDFC0\uD83D\uDC79\uD83C\uDF81\uD83C\uDF89\uD83C\uDF82]+")

// 상수
const val LINE = "§e-----------------------------------------------------"

// public 필드
val mc get() = Minecraft.getMinecraft()!!
val fr get() = mc.fontRendererObj!!
val modFile: File by lazy { Loader.instance().activeModContainer().source }
val logger get() = ZombiesAddon.instance.logger

// 출력 관련 함수
fun addLine() = mc.thePlayer?.addChatMessage(ChatComponentText(LINE)) ?: logger.info("[addLine] $LINE")

@Suppress("LoggingSimilarMessage")
fun addChat(text: String) = mc.thePlayer?.addChatMessage(ChatComponentText(text)) ?: logger.info("[addChat] $text")
fun addChat(chatComponent: IChatComponent) = mc.thePlayer?.addChatMessage(chatComponent) ?: logger.info("[addChat] ${chatComponent.formattedText}")

fun addChatLine(text: String) = mc.thePlayer?.addChatMessage(ChatComponentText("$LINE\n$text\n$LINE")) ?: logger.info("[addChatLine] $LINE\n$text\n$LINE")

fun addTranslatedChat(key: String, vararg any: Any) = addChat(getTranslatedString(key, any = any))

fun addTranslatedChatLine(key: String, vararg any: Any) = addChatLine(getTranslatedString(key, any = any))

fun sendChat(text: String) = mc.thePlayer?.sendChatMessage(text) ?: logger.info("[sendChat] $text")

// 문자열 관련 함수
fun String.withoutColor() = EnumChatFormatting.getTextWithoutFormattingCodes(this)!!

fun String.removeEmoji() = this.replace(emojiPattern, "")

@JvmOverloads
fun getTranslatedString(key: String, withColor: Boolean = true, vararg any: Any): String {
    val text = key.translate(*any)
    return if (withColor) text else text.withoutColor()
}

// 화면 관련 함수
fun getX(): Float = ScaledResolution(mc).scaledWidth.toFloat()
fun getX(text: String): Float = getX() - fr.getStringWidth(text) - 1

fun getY(): Float = ScaledResolution(mc).scaledHeight.toFloat()
fun getYFont(): Float = getY() - fr.FONT_HEIGHT - 1

// 상태 관련 함수
fun isDisable() = !ZAConfig.general.enableMod

fun isHypixel(): Boolean {
    val ip = mc.currentServerData?.serverIP ?: return false
    return ip.matches(Regex("(.+\\.)?(hypixel\\.net)(:25565)?"))
}

fun isPractice(): Boolean {
    val ip = mc.currentServerData?.serverIP ?: return false
    return ip.matches(Regex("(zombies\\.santoss\\.wtf)(:25565)?"))
}

fun isPracticeLobby(): Boolean {
    if (mc.thePlayer == null || mc.theWorld == null) return false
    if (!isPractice()) return false
    val world = mc.theWorld
    val pos = BlockPos(0, 44, 0)
    if (!world.isBlockLoaded(pos)) return false
    return world.getBlockState(pos).block.unlocalizedName == "tile.glass"
}

fun isNotPlayZombies(): Boolean {
    if (mc.thePlayer == null || mc.theWorld == null) return true
    if (Scoreboard.isNotZombies) return true
    return GameManager.game == null
}

fun getServerNumber(): ServerNumber? {
    if (Scoreboard.isNotZombies) return null
    if (ZombiesAddon.instance.debug) return CommandZaDebug.serverNumber
    return runCatching { ServerNumber(Scoreboard.lines[0].replace(serverNumberPattern, "$1")) }.getOrNull()
}

fun getMap(): ZombiesMap? {
    if (ZombiesAddon.instance.debug) return CommandZaDebug.gameMode.map
    val world = mc.theWorld ?: return null
    if (Scoreboard.isNotZombies) return null
    val pos = BlockPos(44, 71, 0)
    if (!world.isBlockLoaded(pos)) return null

    return when (world.getBlockState(pos).block.unlocalizedName) {
        "tile.air" -> ZombiesMap.DEAD_END
        "tile.cloth" -> ZombiesMap.BAD_BLOOD
        "tile.stoneSlab" -> ZombiesMap.ALIEN_ARCADIUM
        "tile.woodSlab" -> ZombiesMap.PRISON
        else -> null
    }
}

fun getDifficulty(): Difficulty? {
    if (ZombiesAddon.instance.debug) return CommandZaDebug.gameMode.difficulty
    if (isNotPlayZombies()) return null
    val line = Scoreboard.lines.getOrNull(10) ?: return null
    return when {
        "Normal" in line -> Difficulty.NORMAL
        "Hard" in line -> Difficulty.HARD
        "RIP" in line -> Difficulty.RIP
        else -> null
    }
}

fun getPlayerStatus(): Array<Status> {
    val playerState = Array(4) { Status.QUIT }
    val lines = Scoreboard.lines

    val playerCount = (lines.size - 11).coerceIn(0, 4)

    for (i in 0..<playerCount) {
        val lineIndex = i + 5

        val status = runCatching { lines[lineIndex].split(":")[1].trim() }.getOrNull()

        playerState[i] = when (status) {
            null -> Status.QUIT
            in rev -> Status.REVIVE
            in dead ->  Status.DEAD
            in quit ->  Status.QUIT
            else -> Status.SURVIVE
        }
    }
    return playerState
}

// 기타 함수
fun String.withNameColor(): String {
    val default = "§f$this"
    if (isNotPlayZombies()) return default
    val scoreboard = mc.theWorld.scoreboard
    val objective = scoreboard.getObjectiveInDisplaySlot(1) ?: return default

    (scoreboard.getSortedScores(objective) ?: return default).forEach {
        val team = scoreboard.getPlayersTeam(it.playerName)
        val line = ScorePlayerTeam.formatPlayerName(team, it.playerName).trim().replace(Regex("[^§A-Za-z0-9_:]"), "")
        if (this in line.withoutColor()) {
            val color = line.substring(0..1)
            return "$color$this"
        }
    }
    return default
}

// private 함수
private val cachedLang = mutableMapOf<String, Properties>()

private fun String.translate(vararg any: Any): String {
    val lang = ZAConfig.general.language
    val langCode = when (lang) {
        Language.AUTO -> return I18n.format(this, *any)
        Language.KO_KR -> "ko_KR"
        Language.EN_US -> "en_US"
        Language.JA_JP -> "ja_JP"
    }

    val prop = cachedLang.getOrPut(langCode) {
        val prop = Properties()
        val res = ResourceLocation(MODID, "lang/$langCode.lang")
        mc.resourceManager.getResource(res).inputStream.use {
            InputStreamReader(it, StandardCharsets.UTF_8).use(prop::load)
        }
        prop
    }

    return prop.getProperty(this, this).format(*any)
}