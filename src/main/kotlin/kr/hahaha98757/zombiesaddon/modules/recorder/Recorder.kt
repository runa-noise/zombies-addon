package kr.hahaha98757.zombiesaddon.modules.recorder

import kr.hahaha98757.zombiesaddon.config.ZAConfig
import kr.hahaha98757.zombiesaddon.game.Game
import kr.hahaha98757.zombiesaddon.modules.recorder.files.GameFile
import kr.hahaha98757.zombiesaddon.utils.addChat
import kr.hahaha98757.zombiesaddon.utils.getTranslatedString
import net.minecraft.event.ClickEvent
import net.minecraft.util.ChatComponentText
import net.minecraft.util.ChatStyle
import kotlin.math.abs

class Recorder(val game: Game, var isCorrected: Boolean = false) {
    private var category = Category()
    private val gameFile = GameFile(game.serverNumber, game.gameMode.map)
    private val isFullRecorded = (game.round == 1)
    private var helicopterTime = 0

    fun reloadCategory() {
        category = Category()
    }

    fun record(doNotSave: Boolean) {
        val config = ZAConfig.recorder
        val save = !doNotSave && config.compareRecord && config.saveRecord
        val sender = MessageSender(category.name, game.round)

        if (config.showGameTime && isFullRecorded && isCorrected) processGameTime(sender, save)
        if (config.showSegment && (game.round != 1 || isCorrected)) processSegment(sender, save)
        if (!(game.gameEnd && game.helicopter) && save) gameFile.segments[game.round] = game.timer.roundTick

        sender.send()
    }

    private fun processGameTime(sender: MessageSender, save: Boolean) {
        val categoryFile = category.getByGameMode(game.gameMode)
        val round = if (game.helicopter) 31 else game.round
        val pb = categoryFile.personalBests[round]
        val gameTime = game.timer.gameTick

        if (game.gameEnd && game.isWin && game.helicopter) {
            if (save && (pb == 0 || helicopterTime < pb)) categoryFile.personalBests[round] = helicopterTime
        } else if (game.helicopter) helicopterTime = gameTime
        else if (save && (pb == 0 || gameTime < pb)) categoryFile.personalBests[round] = gameTime


        val oldTime = if (ZAConfig.recorder.compareRecord) pb else 0
        if (game.gameEnd && game.isWin && game.helicopter) sender.setEscape(helicopterTime + 2400, if (oldTime != 0) oldTime + 2400 else 0, game.lastRound) // 2400 ticks = 2 minutes
        else if (game.helicopter) sender.setHelicopter(helicopterTime, oldTime, game.lastRound)
        else sender.setGameTime(gameTime, oldTime)
    }

    private fun processSegment(sender: MessageSender, save: Boolean) {
        if (game.helicopter) return
        val categoryFile = category.getByGameMode(game.gameMode)
        val bestSegment = categoryFile.bestSegments[game.round]
        val segment = game.timer.roundTick

        if (save && (bestSegment == 0 || segment < bestSegment)) categoryFile.bestSegments[game.round] = segment

        val oldTime = if (ZAConfig.recorder.compareRecord) bestSegment else 0
        sender.setSegment(segment, oldTime)

        val w3 = game.roundData?.waves?.last()?.ticks!!

        val cleanup = segment - w3

        sender.setCleanup(cleanup)
    }
}

private class MessageSender(categoryName: String, private val round: Int) {
    companion object {
        const val BAR = "§l§a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬"
    }

    private val message = ChatComponentText(
        "$BAR\n${getTranslatedString("zombiesaddon.modules.recorder.category", true, "§d$categoryName")}"
    )
    private var hasEntry = false

    fun setGameTime(newTime: Int, oldTime: Int) {
        val timeString = formattedTime(newTime)
        val deltaString = deltaString(newTime, oldTime)
        add(
            noticeKey = "zombiesaddon.modules.recorder.newPb",
            line = getTranslatedString("zombiesaddon.modules.recorder.gameTime", true, "§c$round", "§a$timeString§9$deltaString"),
            copy = getTranslatedString("zombiesaddon.modules.recorder.gameTime", false, round, copyTime(timeString, deltaString)),
            isNewBest = newTime < oldTime
        )
    }

    fun setSegment(newTime: Int, oldTime: Int) {
        val timeString = formattedTime(newTime)
        val deltaString = deltaString(newTime, oldTime)
        add(
            noticeKey = "zombiesaddon.modules.recorder.newBestSegment",
            line = getTranslatedString("zombiesaddon.modules.recorder.segment", true, "§c$round", "§a$timeString§9$deltaString"),
            copy = getTranslatedString("zombiesaddon.modules.recorder.segment", false, round, copyTime(timeString, deltaString)),
            isNewBest = newTime < oldTime
        )
    }

    fun setCleanup(time: Int) {
        val timeString = formattedTime(time)
        add(
            noticeKey = "zombiesaddon.modules.recorder.cleanup",
            line = getTranslatedString("zombiesaddon.modules.recorder.cleanup", true, "§c$round", "§a$timeString"),
            copy = getTranslatedString("zombiesaddon.modules.recorder.cleanup", false, round, timeString),
            isNewBest = false
        )
    }

    fun setHelicopter(newTime: Int, oldTime: Int, round: Int) {
        val timeString = formattedTime(newTime)
        val deltaString = deltaString(newTime, oldTime)
        add(
            noticeKey = "zombiesaddon.modules.recorder.newPb",
            line = getTranslatedString("zombiesaddon.modules.recorder.helicopter", true, "§a$timeString§9$deltaString", "§c$round"),
            copy = getTranslatedString("zombiesaddon.modules.recorder.helicopter", false, copyTime(timeString, deltaString), round),
            isNewBest = newTime < oldTime
        )
        message.appendSibling(ChatComponentText("\n${getTranslatedString("zombiesaddon.modules.recorder.helicopter.comment")}"))
    }

    fun setEscape(newTime: Int, oldTime: Int, round: Int) {
        val timeString = formattedTime(newTime)
        val deltaString = deltaString(newTime, oldTime)
        add(
            noticeKey = "zombiesaddon.modules.recorder.newPb",
            line = getTranslatedString("zombiesaddon.modules.recorder.escape", true, "§a$timeString§9$deltaString", "§c$round"),
            copy = getTranslatedString("zombiesaddon.modules.recorder.escape", false, copyTime(timeString, deltaString), round),
            isNewBest = newTime < oldTime
        )
    }

    private fun add(noticeKey: String, line: String, copy: String, isNewBest: Boolean) {
        val notice = if (ZAConfig.recorder.newBestNotice && isNewBest)
            "\n${getTranslatedString(noticeKey)}" else ""
        val entry = ChatComponentText("$notice\n$line")
        entry.chatStyle = ChatStyle().setChatClickEvent(ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, copy))
        message.appendSibling(entry)
        hasEntry = true
    }

    fun send() {
        if (!hasEntry) return
        message.appendSibling(ChatComponentText("\n$BAR"))
        addChat(message)
    }

    private fun deltaString(newTime: Int, oldTime: Int) =
        if (oldTime != 0) " ${formattedDelta(newTime, oldTime)}" else ""

    private fun copyTime(timeString: String, deltaString: String) =
        if (deltaString.isEmpty()) timeString else "$timeString ($deltaString)"

    private fun formattedTime(time: Int): String {
        val second = time / 20
        val centisecond = (time % 20) * 5

        return if (second < 3600) "${second / 60}:%02d.%02d".format(second % 60, centisecond)
        else "${second / 3600}:%02d:%02d.%02d".format((second % 3600) / 60, second % 60, centisecond)
    }

    private fun formattedDelta(newTime: Int, oldTime: Int): String {
        val delta = newTime - oldTime
        val sign = if (delta < 0) "-" else "+"
        val tick = abs(delta)

        val second = tick / 20
        val centisecond = (tick % 20) * 5
        if (!ZAConfig.recorder.deltaFormatting) return "$sign$second.%02d".format(centisecond)

        return if (second < 60) "$sign$second.%02d".format(centisecond)
        else if (second < 3600) "$sign${second / 60}:%02d.%02d".format(second % 60, centisecond)
        else "$sign${second / 3600}:%02d:%02d.%02d".format((second % 3600) / 60, second % 60, centisecond)
    }
}