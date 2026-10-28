package kr.hahaha98757.zombiesaddon.utils

import java.io.File
import java.io.RandomAccessFile

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