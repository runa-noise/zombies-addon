package kr.runanoise.zombiesaddon.update

import kr.runanoise.zombiesaddon.gui.GuiDownloadWaiting
import kr.runanoise.zombiesaddon.update.UpdateChecker.ctx
import kr.runanoise.zombiesaddon.update.UpdateChecker.latest
import kr.runanoise.zombiesaddon.utils.FileRemoverLauncher
import kr.runanoise.zombiesaddon.utils.FileRemoverLauncher.requireMainMethod
import kr.runanoise.zombiesaddon.utils.logger
import kr.runanoise.zombiesaddon.utils.mc
import kr.runanoise.zombiesaddon.utils.modFile
import org.apache.commons.io.IOUtils
import java.io.File
import java.net.URL
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import javax.net.ssl.HttpsURLConnection

object AutoUpdater {
    private val downloadUrl
        get() = "https://github.com/runa-noise/zombies-addon/releases/download/$latest/ZombiesAddon1.8.9-$latest.jar"

    fun autoUpdate() = Thread {
        val tempMod = File(modFile.parentFile, ".temp/ZombiesAddon1.8.9-$latest.jar.temp")
        val newMod = File(modFile.parentFile, "ZombiesAddon1.8.9-$latest.jar")
        try {
            tempMod.parentFile.mkdirs()
            val connection = URL(downloadUrl).openConnection() as HttpsURLConnection?
            if (connection != null && ctx != null) connection.sslSocketFactory = ctx!!.socketFactory

            connection!!.requestMethod = "GET"
            connection.connect()

            Files.copy(connection.inputStream, tempMod.toPath(), StandardCopyOption.REPLACE_EXISTING)

            if (!verifyModFile(tempMod)) throw Exception("다운로드한 파일의 무결성 검사에 실패했습니다.")

            Files.move(tempMod.toPath(), newMod.toPath(),
                StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE)

            requireMainMethod(newMod, "kr.runanoise.zombiesaddon.utils.FileRemover")
            mc.addScheduledTask { FileRemoverLauncher.runAndQuit(arrayOf(modFile), newMod) }
        } catch (e: Exception) {
            if (newMod.exists()) newMod.delete()
            logger.error("자동 업데이트 중 오류 발생.", e)
            mc.addScheduledTask { GuiDownloadWaiting.failed = true }
        } finally {
            if (tempMod.exists()) tempMod.delete()
            tempMod.parentFile.delete()
        }
    }.start()

    private fun verifyModFile(file: File): Boolean {
        val connection = URL("$downloadUrl.sha256").openConnection() as HttpsURLConnection?
        if (connection != null && ctx != null) connection.sslSocketFactory = ctx!!.socketFactory

        connection!!.requestMethod = "GET"
        connection.connect()

        val expectedHash = IOUtils.toString(connection.inputStream, StandardCharsets.UTF_8).trim()
        logger.info("예상되는 SHA-256: $expectedHash")
        val actualHash = calculateSHA256(file)
        return expectedHash.equals(actualHash, ignoreCase = true)
    }

    private fun calculateSHA256(file: File): String {
        val digest = java.security.MessageDigest.getInstance("SHA-256")
        file.inputStream().use { input ->
            val buffer = ByteArray(8192)
            var bytesRead: Int
            while (input.read(buffer).also { bytesRead = it } > 0) digest.update(buffer, 0, bytesRead)
        }
        return digest.digest().joinToString("") { "%02x".format(it) }.also { logger.info("실제 SHA-256: $it") }
    }
}