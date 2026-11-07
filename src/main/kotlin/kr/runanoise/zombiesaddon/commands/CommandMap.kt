package kr.runanoise.zombiesaddon.commands

import kr.runanoise.zombiesaddon.utils.sendChat
import net.minecraft.command.ICommandSender
import net.minecraftforge.client.ClientCommandHandler

abstract class CommandMap: CustomCommandBase() {
    companion object {
        private val commands = listOf(
            "de" to "dead_end",
            "bb" to "bad_blood",
            "aa" to "alien_arcadium",
            "pr" to "prison"
        )

        fun register() = commands.forEach { (commandName, map) ->
            ClientCommandHandler.instance.registerCommand(object: CustomCommandBase() {
                override fun getCommandName() = commandName
                override fun getCommandUsage(sender: ICommandSender?) = "/$commandName"
                override fun runCommand(sender: ICommandSender, args: Array<String>) = sendChat("/play arcade_zombies_$map")
            })
        }
    }
}