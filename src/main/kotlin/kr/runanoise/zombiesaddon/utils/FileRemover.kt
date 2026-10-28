package kr.runanoise.zombiesaddon.utils

import net.minecraft.client.Minecraft
import java.io.File
import java.io.RandomAccessFile
import java.lang.reflect.Modifier
import java.net.URLClassLoader

object FileRemover {
    @JvmStatic
    fun main(args: Array<String>) {
        if (args.isEmpty()) return
        Thread.sleep(1000)

        val targets = args[0].split("|").map { File(it) }

        for (file in targets) {
            if (!file.exists()) continue
            if (file.isDirectory) continue
            waitUntilUnlocked(file)
            deleteWithRetry(file)
        }
    }

    private fun waitUntilUnlocked(file: File, timeout: Long = 10000) {
        var time = timeout
        while (isFileLocked(file)) {
            Thread.sleep(1000)
            time -= 1000
            if (time <= 0) return
        }
    }

    private fun isFileLocked(file: File) = file.exists() && try {
        RandomAccessFile(file, "rw").use { raf ->
            val lock = raf.channel.tryLock()
            if (lock != null) {
                lock.release()
                false
            } else true
        }
    } catch (_: Exception) {
        true
    }

    private fun deleteWithRetry(file: File): Unit = repeat(10) {
        if (!file.exists() || file.delete()) return
        Thread.sleep(500)
    }
}

object FileRemoverLauncher {
    /**
     * @throws IllegalStateException 메인 메서드가 없거나 Minecraft 인스턴스를 찾을 수 없는 경우
     */
    internal fun runAndQuit(targets: Array<File>, jar: File? = null, className: String = "kr.runanoise.zombiesaddon.utils.FileRemover") {
        val jarFile = jar ?: modFile
        requireMainMethod(jarFile, className)
        runCatching { Minecraft.getMinecraft() }.getOrNull() ?: error("Minecraft not found")

        logger.info("FileRemover 실행 및 게임 종료를 시작합니다.")
        val joinedPaths = targets.joinToString(separator = "|") { it.absolutePath }
        val jvm = jvmPath

        val command = if (isWindows) {
            listOf(
                "cmd", "/c", "start", "",
                jvm,
                "-cp", jarFile.absolutePath,
                className,
                joinedPaths
            )
        } else {
            listOf(
                "sh", "-c",
                "nohup \"$jvm\" -cp \"${jarFile.absolutePath}\" $className \"$joinedPaths\" >/dev/null 2>&1 &"
            )
        }
        logger.info("실행할 명령: ${command.joinToString(" ")}")

        ProcessBuilder(command).start()
        Thread.sleep(1000)
        mc.shutdown()
    }

    /**
     * Minecraft API 불필요.
     *
     * @throws IllegalStateException 메인 메서드가 없는 경우
     */
    fun requireMainMethod(jar: File, className: String) {
        runCatching {
            URLClassLoader(arrayOf(jar.toURI().toURL())).use {
                val clazz = Class.forName(className, false, it)
                val main = clazz.getMethod("main", Array<String>::class.java)
                val mod = main.modifiers
                if (Modifier.isPublic(mod) && Modifier.isStatic(mod) && main.returnType == Void.TYPE) return
            }
        }
        error("The main method not found")
    }

    private val jvmPath: String get() {
        val exec = System.getProperty("java.home") + File.separator + "bin" + File.separator + "javaw"
        return if (isWindows) "$exec.exe" else exec
    }

    private val isWindows = "win" in System.getProperty("os.name").lowercase()
}