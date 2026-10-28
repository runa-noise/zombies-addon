package kr.runanoise.zombiesaddon.commands

import net.minecraftforge.client.ClientCommandHandler

object Commands {
    fun registerCommands() {
        ClientCommandHandler.instance.registerCommand(CommandDE)
        ClientCommandHandler.instance.registerCommand(CommandBB)
        ClientCommandHandler.instance.registerCommand(CommandAA)
        ClientCommandHandler.instance.registerCommand(CommandPR)
        ClientCommandHandler.instance.registerCommand(CommandPowerupPatterns)
        ClientCommandHandler.instance.registerCommand(CommandSla)
        ClientCommandHandler.instance.registerCommand(CommandZombiesAddon)
        ClientCommandHandler.instance.registerCommand(CommandZsv)
        ClientCommandHandler.instance.registerCommand(CommandZsvLines)
        ClientCommandHandler.instance.registerCommand(CommandAutoSplits)
        ClientCommandHandler.instance.registerCommand(CommandZaDebug)
        ClientCommandHandler.instance.registerCommand(CommandRecorder)
    }
}