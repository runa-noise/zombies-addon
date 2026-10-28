package kr.runanoise.zombiesaddon.commands

import kr.runanoise.zombiesaddon.modules.PowerupPatterns
import kr.runanoise.zombiesaddon.utils.addTranslatedChat
import net.minecraft.command.ICommandSender
import net.minecraft.command.WrongUsageException
import net.minecraft.util.BlockPos

object CommandPowerupPatterns: CustomCommandBase() {
    override fun getCommandName() = "poweruppatterns"
    override fun getCommandUsage(sender: ICommandSender?) = "zombiesaddon.commands.poweruppatterns.usage"
    override fun getCommandAliases() = listOf("pow")
    override fun runCommand(sender: ICommandSender, args: Array<String>) {
        if (args.isEmpty()) throw WrongUsageException(getCommandUsage(null))

        when (args[0]) {
            "ins" -> {
                if (args.size == 1) PowerupPatterns.fields?.insTimer = true
                else {
                    when (args[1]) {
                        "reset" -> {
                            addTranslatedChat("zombiesaddon.commands.poweruppatterns.success.reset", "§cInsta Kill")
                            PowerupPatterns.fields?.queuedInsPattern = 0
                        }
                        "2", "3" -> {
                            addTranslatedChat("zombiesaddon.commands.poweruppatterns.success.set", "§cInsta Kill", args[1])
                            PowerupPatterns.fields?.queuedInsPattern = args[1].toInt()
                        }
                        else -> throw WrongUsageException("/poweruppatterns ins [reset|2|3]")
                    }
                }
            }
            "max" -> {
                if (args.size == 1) PowerupPatterns.fields?.maxTimer = true
                else {
                    when (args[1]) {
                        "reset" -> {
                            addTranslatedChat("zombiesaddon.commands.poweruppatterns.success.reset", "§9Max Ammo")
                            PowerupPatterns.fields?.queuedMaxPattern = 0
                        }
                        "2", "3" -> {
                            addTranslatedChat("zombiesaddon.commands.poweruppatterns.success.set", "§9Max Ammo", args[1])
                            PowerupPatterns.fields?.queuedMaxPattern = args[1].toInt()
                        }
                        else -> throw WrongUsageException("/poweruppatterns max [reset|2|3]")
                    }
                }
            }
            "ss" -> {
                if (args.size == 1) PowerupPatterns.fields?.ssTimer = true
                else {
                    when (args[1]) {
                        "reset" -> {
                            addTranslatedChat("zombiesaddon.commands.poweruppatterns.success.reset", "§5Shopping Spree")
                            PowerupPatterns.fields?.queuedSsPattern = 0
                        }
                        "5", "6", "7" -> {
                            addTranslatedChat("zombiesaddon.commands.poweruppatterns.success.set", "§5Shopping Spree", args[1])
                            PowerupPatterns.fields?.queuedSsPattern = args[1].toInt()
                        }
                        else -> throw WrongUsageException("/poweruppatterns ss [reset|5|6|7]")
                    }
                }
            }
            "dg" -> PowerupPatterns.fields?.dgTimer = true
            "car" -> PowerupPatterns.fields?.carTimer = true
            "bg" -> PowerupPatterns.fields?.bgTimer = true
            else -> throw WrongUsageException(getCommandUsage(null))
        }
    }

    override fun addTabCompletionOptions(sender: ICommandSender?, args: Array<String?>, pos: BlockPos?) =
        when (args.size) {
            1 -> getListOfStringsMatchingLastWord(args, "ins", "max", "ss", "dg", "car", "bg")
            2 -> if (args[0] == "ins" || args[0] == "max") getListOfStringsMatchingLastWord(args, "reset", "2", "3")
            else if (args[0] == "ss") getListOfStringsMatchingLastWord(args, "reset", "5", "6", "7")
            else null
            else -> null
        }
}