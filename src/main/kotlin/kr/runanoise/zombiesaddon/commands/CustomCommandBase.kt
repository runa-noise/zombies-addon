package kr.runanoise.zombiesaddon.commands

import kr.runanoise.zombiesaddon.utils.mc
import net.minecraft.command.CommandBase
import net.minecraft.command.ICommandSender
import net.minecraft.util.BlockPos

abstract class CustomCommandBase: CommandBase() {
    final override fun getRequiredPermissionLevel() = 0
    final override fun processCommand(sender: ICommandSender?, args: Array<String>) {
        if (sender == mc.thePlayer) runCommand(sender, args)
    }
    abstract fun runCommand(sender: ICommandSender, args: Array<String>)
    override fun addTabCompletionOptions(sender: ICommandSender?, args: Array<String?>, pos: BlockPos?): List<String>? = null
}