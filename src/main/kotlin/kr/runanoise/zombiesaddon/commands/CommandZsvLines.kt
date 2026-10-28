package kr.runanoise.zombiesaddon.commands

import kr.runanoise.zombiesaddon.modules.ZombiesStratViewer
import kr.runanoise.zombiesaddon.utils.addTranslatedChat
import net.minecraft.command.ICommandSender
import net.minecraft.command.NumberInvalidException
import net.minecraft.command.WrongUsageException

object CommandZsvLines: CustomCommandBase() {
    override fun getCommandName() = "zsvlines"
    override fun getCommandUsage(sender: ICommandSender?) = "zombiesaddon.commands.zsvlines.usage"
    override fun runCommand(sender: ICommandSender, args: Array<String>) {
        if (args.isEmpty()) throw WrongUsageException(getCommandUsage(null))

        try {
            val i = args[0].toInt()
            if (i < 1) throw NumberInvalidException("commands.generic.num.tooSmall", i, 1)

            ZombiesStratViewer.linesOfView = i
            ZombiesStratViewer.refreshActualLines()
            addTranslatedChat("zombiesaddon.commands.zsvlines.success", i)
        } catch (_: NumberFormatException) {
            throw NumberInvalidException("commands.generic.num.invalid", args[0])
        }
    }
}