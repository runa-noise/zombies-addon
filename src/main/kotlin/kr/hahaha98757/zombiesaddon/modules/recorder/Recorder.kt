package kr.hahaha98757.zombiesaddon.modules.recorder

import kr.hahaha98757.zombiesaddon.config.ZAConfig
import kr.hahaha98757.zombiesaddon.game.Game
import kr.hahaha98757.zombiesaddon.modules.recorder.files.GameFile
import kr.hahaha98757.zombiesaddon.utils.addChat
import kr.hahaha98757.zombiesaddon.utils.getTranslatedString
import net.minecraft.event.ClickEvent
import net.minecraft.util.ChatComponentText
import net.minecraft.util.ChatStyle

class Recorder(val game: Game) {
    private var category = Category()
    private val gameFile = GameFile(game.serverNumber, game.gameMode.map)
    private val isFullRecorded = (game.round == 1)
    var isCorrected = false

    fun reloadCategory() {
        category = Category()
    }

    fun record(save: Boolean) {
        if (game.round != 1 || isCorrected) compareSegment(save)
        if (isFullRecorded && isCorrected) comparePb(save)
        if (save) gameFile.segments[game.round] = game.timer.roundTick
    }

    private fun compareSegment(save: Boolean) {
        if (game.escape) return
        if (!save) {
            val sender = MessageSender(category.name, game.round, game.timer.roundTick, 0)
            sender.roundSplit()
            sender.send()
            return
        }
        val categoryFile = category.getByGameMode(game.gameMode)
        val bestSegment = categoryFile.bestSegments[game.round]
        val roundTick = game.timer.roundTick

        if (bestSegment == 0 || roundTick < bestSegment) categoryFile.bestSegments[game.round] = roundTick
        val sender = MessageSender(category.name, game.round, roundTick, bestSegment)
        sender.roundSplit()
        sender.send()
    }

    private fun comparePb(save: Boolean) {
        if (!save) {
            val sender = MessageSender(category.name, if (game.escape) 31 else game.round, game.timer.gameTick, 0)
            if (game.escape) sender.helicopterSplit()
            else sender.gameSplit()
            sender.send()
            return
        }
        val categoryFile = category.getByGameMode(game.gameMode)
        val round = if (game.escape) 31 else game.round
        val pb = categoryFile.personalBests[round]
        val gameTick = game.timer.gameTick

        if (pb == 0 || gameTick < pb) categoryFile.personalBests[round] = gameTick
        val sender = MessageSender(category.name, round, gameTick, pb)
        if (game.escape) sender.helicopterSplit()
        else sender.gameSplit()
        sender.send()
    }
}

private class MessageSender(categoryName: String, private val round: Int, private val newTime: Int, private val oldTime: Int) {
    companion object {
        const val BAR = "§l§a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬"
    }

    private val deltaString = if (oldTime != 0) " ${formattedDelta(newTime, oldTime)}" else ""
    private val timeString = formattedTime(newTime)
    private var recordMessage = "$BAR\n${getTranslatedString("zombiesaddon.modules.recorder.category", true, "§d$categoryName")}"
    private var copyString = ""

    fun gameSplit() {
        val notice = if (ZAConfig.recorder.pbNotice && newTime < oldTime)
            "\n${getTranslatedString("zombiesaddon.modules.recorder.newPb")}" else ""
        recordMessage += "$notice\n${getTranslatedString("zombiesaddon.modules.recorder.pb", true, "§c$round", "§a$timeString§9$deltaString")}"
        copyString = getTranslatedString("zombiesaddon.modules.recorder.pb", false, round, timeString + if (deltaString.isEmpty()) "" else " ($deltaString)")
    }

    fun roundSplit() {
        val notice = if (ZAConfig.recorder.pbNotice && newTime < oldTime)
            "\n${getTranslatedString("zombiesaddon.modules.recorder.newBestSegment")}" else ""
        recordMessage += "$notice\n${getTranslatedString("zombiesaddon.modules.recorder.bestSegment", true, "§c$round", "§a$timeString§9$deltaString")}"
        copyString = getTranslatedString("zombiesaddon.modules.recorder.bestSegment", false, round, timeString + if (deltaString.isEmpty()) "" else " ($deltaString)")
    }

    fun helicopterSplit() {
        val notice = if (ZAConfig.recorder.pbNotice && newTime < oldTime)
            "\n${getTranslatedString("zombiesaddon.modules.recorder.newPb")}" else ""
        recordMessage += "$notice\n${getTranslatedString("zombiesaddon.modules.recorder.helicopter", true, "§a$timeString§9$deltaString")}"
        copyString = getTranslatedString("zombiesaddon.modules.recorder.helicopter", false, timeString + if (deltaString.isEmpty()) "" else " ($deltaString)")
    }

    fun send() {
        val message = ChatComponentText("$recordMessage\n$BAR")
        message.chatStyle = ChatStyle().setChatClickEvent(ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, copyString))
        addChat(message)
    }

    private fun formattedTime(time: Int): String {
        val time = time * 50
        return "${time/60000}:%02d.${(time%1000)/100}${(time%100)/10}".format((time%60000)/1000)
    }

    private fun formattedDelta(newTime: Int, oldTime: Int): String {
        val delta = (newTime - oldTime) / 20.0
        return "%+.2f".format(delta)
    }
}