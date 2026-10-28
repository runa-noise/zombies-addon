package kr.runanoise.zombiesaddon.commands

import kr.runanoise.zombiesaddon.utils.sendChat
import net.minecraft.command.ICommandSender

abstract class CommandMap: CustomCommandBase() {
    abstract fun getMap(): String
    final override fun getCommandUsage(sender: ICommandSender?) = "/$commandName"
    final override fun runCommand(sender: ICommandSender, args: Array<String>) = sendChat("/play arcade_zombies_${getMap()}")
}

object CommandDE: CommandMap() {
    override fun getCommandName() = "de"
    override fun getMap() = "dead_end"
}

object CommandBB: CommandMap() {
    override fun getCommandName() = "bb"
    override fun getMap() = "bad_blood"
}

object CommandAA: CommandMap() {
    override fun getCommandName() = "aa"
    override fun getMap() = "alien_arcadium"
}

object CommandPR: CommandMap() {
    override fun getCommandName() = "pr"
    override fun getMap() = "prison"
}