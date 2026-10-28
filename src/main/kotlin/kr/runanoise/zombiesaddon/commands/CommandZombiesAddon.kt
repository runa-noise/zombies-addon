package kr.runanoise.zombiesaddon.commands

import kr.runanoise.zombiesaddon.MODID
import kr.runanoise.zombiesaddon.gui.hudposition.ConfigGui
import kr.runanoise.zombiesaddon.update.UpdateChecker
import kr.runanoise.zombiesaddon.utils.DelayedTask
import kr.runanoise.zombiesaddon.utils.mc
import net.minecraft.command.ICommandSender
import net.minecraft.command.WrongUsageException
import net.minecraft.util.BlockPos

object CommandZombiesAddon: CustomCommandBase() {
    override fun getCommandName() = MODID
    override fun getCommandUsage(sender: ICommandSender?) = "/zombiesaddon <hud|checkUpdate>"
    override fun runCommand(sender: ICommandSender, args: Array<String>) {
        if (args.isEmpty()) throw WrongUsageException(getCommandUsage(null))
        when (args[0]) {
            "hud" -> DelayedTask { mc.displayGuiScreen(ConfigGui) }
            "checkUpdate" -> UpdateChecker.initAndCheck()
            "test" -> onTest()
            else -> throw WrongUsageException(getCommandUsage(null))
        }
    }

    override fun addTabCompletionOptions(sender: ICommandSender?, args: Array<String?>, pos: BlockPos?): List<String>? =
        if (args.size == 1) getListOfStringsMatchingLastWord(args, "hud", "checkUpdate")
        else null

    private fun onTest() {
        throw WrongUsageException(getCommandUsage(null))
    }
}