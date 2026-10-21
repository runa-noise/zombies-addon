package kr.hahaha98757.zombiesaddon.modules

import kr.hahaha98757.zombiesaddon.config.ZAConfig
import kr.hahaha98757.zombiesaddon.utils.addTranslatedChat
import kr.hahaha98757.zombiesaddon.utils.logger
import kr.hahaha98757.zombiesaddon.utils.mc
import java.net.Socket


object AutoSplits {
    fun startOrSplit() {
        if (!ZAConfig.autoSplits.enable) return
        sendCommand("startorsplit")
    }

    fun pause() {
        if (!ZAConfig.autoSplits.enable) return
        sendCommand("pause")
    }

    fun sendCommand(command: String) = Thread {
        try {
            Socket(ZAConfig.autoSplits.host, ZAConfig.autoSplits.port).use {
                val output = it.getOutputStream().bufferedWriter()

                output.write("$command\r\n")
                output.flush()
            }
        } catch (e: Exception) {
            logger.warn("LiveSplit에 명령어를 전달하는데 실패했습니다: $command", e)
            mc.addScheduledTask { addTranslatedChat("zombiesaddon.modules.autoSplits.failed", command) }
        }
    }.start()
}