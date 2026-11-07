package kr.runanoise.zombiesaddon.commands

import net.minecraftforge.client.ClientCommandHandler

object Commands {
    fun registerCommands() {
        CommandMap.register()
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