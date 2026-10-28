package kr.runanoise.zombiesaddon.update

import com.google.gson.JsonParser
import kr.runanoise.zombiesaddon.VERSION
import kr.runanoise.zombiesaddon.gui.GuiUpdateScreen
import kr.runanoise.zombiesaddon.utils.*
import net.minecraft.event.ClickEvent
import net.minecraft.event.HoverEvent
import net.minecraft.util.ChatComponentText
import net.minecraft.util.ChatComponentTranslation
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.event.entity.EntityJoinWorldEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import org.apache.commons.io.IOUtils
import java.net.URL
import java.nio.charset.StandardCharsets
import java.security.KeyStore
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.KeyManagerFactory
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory

object UpdateChecker {
    private const val LATEST_URL = "https://raw.githubusercontent.com/runa-noise/zombies-addon/main/update.json"
    private const val DEV_URL = "https://raw.githubusercontent.com/runa-noise/zombies-addon/dev/update.json"
    private val current: Version
    var latest = Version.ZERO
    private var dev = Version.ZERO
    private var isCorrect = false
    init {
        current = Version.runCatching {
            fromString(VERSION).also { isCorrect = true }
        }.getOrElse {
            logger.error(it)
            isCorrect = false
            Version.ZERO
        }
    }

    internal var ctx: SSLContext?
    init {
        try {
            val myKeyStore = KeyStore.getInstance("JKS")
            myKeyStore.load(UpdateChecker::class.java.getResourceAsStream("/mykeystore.jks"), "changeit".toCharArray())
            val kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm())
            val tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
            kmf.init(myKeyStore, null)
            tmf.init(myKeyStore)
            ctx = SSLContext.getInstance("TLS")
            ctx!!.init(kmf.keyManagers, tmf.trustManagers, null)
        } catch (e: Throwable) {
            logger.error("SSLContext 초기화 실패. 일부 JVM에서 업데이트 확인이 불가능합니다.", e)
            ctx = null
        }
    }

    fun initAndCheck() = Thread {
        if (!isCorrect) return@Thread
        latest = runCatching { getVersion(LATEST_URL) }.getOrElse {
            logger.warn("최신 버전 가져오는데 실패했습니다.", it)
            Version.ZERO
        }
        dev = runCatching { getVersion(DEV_URL) }.getOrElse {
            logger.warn("개발 버전 가져오는데 실패했습니다.", it)
            latest
        }
        logger.info("현재 버전: $current, 최신 버전: $latest, 개발 버전: $dev")
        mc.addScheduledTask { check() }
    }.start()

    fun check(displayGui: Boolean = true) {
        if (!isCorrect) return
        if (dev == Version.ZERO) {
            addTranslatedChat("zombiesaddon.update.failed")
            return
        }

        // 이 이후로 dev, latest 모두 zero가 아님이 보장됨
        if (current > dev) { // 미공개 버전 사용 중
            if (current.versionType == VersionType.ALPHA) {
                addLine()
                addTranslatedChat("zombiesaddon.update.usingDev")
                addTranslatedChat(getDevWarningKey(current.versionType))
                addLine()
            }
            return
        }

        if (current.x < latest.x) {
            mc.displayGuiScreen(GuiUpdateScreen.getInstance(true)) // 필수 업데이트
            return
        }

        if (latest == dev) {
            if (current != latest && displayGui) mc.displayGuiScreen(GuiUpdateScreen.getInstance(false)) // 새로운 최신 버전
        } else {
            if (current == dev) usingDev() // 개발 버전 사용 중
            else newDev() // 새로운 개발 버전
        }
    }

    private fun usingDev() {
        if (current.versionType == VersionType.RELEASE) return
        addLine()
        addTranslatedChat("zombiesaddon.update.usingDev")
        addTranslatedChat(getDevWarningKey(current.versionType))
        addLine()
    }

    private fun newDev() {
        if (dev.versionType == VersionType.RELEASE) return
        val url = ChatComponentText(getTranslatedString("zombiesaddon.update.downloadText")).apply {
            chatStyle.chatClickEvent =
                ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/runa-noise/zombies-addon/releases")
            chatStyle.chatHoverEvent =
                HoverEvent(HoverEvent.Action.SHOW_TEXT, ChatComponentTranslation("zombiesaddon.update.downloadHover"))
        }
        addLine()
        ChatComponentText("").run {
            appendText(getTranslatedString("zombiesaddon.update.newDev"))
            appendText(" ")
            appendSibling(url)
            appendText("\n")
            appendText(getTranslatedString("zombiesaddon.update.currentVer", true, VERSION))
            appendText("\n")
            appendText(getTranslatedString("zombiesaddon.update.devVer", true, dev))
            appendText("\n")
            appendText(getTranslatedString(getDevWarningKey(dev.versionType)))
            addChat(this)
        }
        addLine()
    }

    private fun getDevWarningKey(type: VersionType) = when (type) {
        VersionType.ALPHA -> "zombiesaddon.update.alpha"
        VersionType.BETA -> "zombiesaddon.update.beta"
        VersionType.PRE_RELEASE -> "zombiesaddon.update.pre"
        VersionType.RELEASE_CANDIDATE -> "zombiesaddon.update.rc"
        VersionType.RELEASE -> ""
    }

    private fun getVersion(url: String): Version {
        val url = URL(url)
        val connection = url.openConnection() as HttpsURLConnection?
        if (connection != null && ctx != null) connection.sslSocketFactory = ctx!!.socketFactory

        connection!!.requestMethod = "GET"
        connection.connectTimeout = 60000
        connection.readTimeout = 60000

        connection.inputStream.use {
            val jsonResponse = IOUtils.toString(it, StandardCharsets.UTF_8)

            val json = JsonParser().parse(jsonResponse).asJsonObject
            return Version.fromString(json.get("version").asString)
        }
    }
}

object UpdateCheckerHandler {
    @SubscribeEvent
    fun onPlayerJoin(event: EntityJoinWorldEvent) {
        if (event.entity != mc.thePlayer) return
        UpdateChecker.check(false)
        MinecraftForge.EVENT_BUS.unregister(this)
    }
}