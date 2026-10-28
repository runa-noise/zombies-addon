package kr.runanoise.zombiesaddon.commands

import kr.runanoise.zombiesaddon.ZombiesAddon
import kr.runanoise.zombiesaddon.data.ServerNumber
import kr.runanoise.zombiesaddon.enums.Difficulty
import kr.runanoise.zombiesaddon.enums.GameMode
import kr.runanoise.zombiesaddon.enums.ZombiesMap
import kr.runanoise.zombiesaddon.game.GameManager
import kr.runanoise.zombiesaddon.modules.NotLast
import kr.runanoise.zombiesaddon.utils.*
import net.minecraft.command.ICommandSender
import net.minecraft.command.NumberInvalidException
import net.minecraft.command.WrongUsageException
import net.minecraft.util.BlockPos

object CommandZaDebug: CustomCommandBase() {
    var isNotZombies = true
    var serverNumber: ServerNumber? = null
    var gameMode = GameMode.DEAD_END_NORMAL


    private var addMessage = false
    override fun getCommandName() = "za_debug"

    override fun getCommandUsage(sender: ICommandSender?) = "/za_debug [isNotZombies|serverNumber|gameMode|new|pass|helicopter|end|remove]"
    override fun runCommand(sender: ICommandSender, args: Array<String>) {
        if (args.isEmpty()) {
            ZombiesAddon.instance.debug = !ZombiesAddon.instance.debug
            addTranslatedChat("zombiesaddon.debug.toggle", if (ZombiesAddon.instance.debug) "§aon" else "§coff")
            if (!addMessage) addTranslatedChatLine("zombiesaddon.debug.info")
            addMessage = true
            return
        }
        if (!ZombiesAddon.instance.debug) {
            addTranslatedChat("zombiesaddon.debug.disabled")
            return
        }
        when (args[0]) {
            "isNotZombies" -> {
                if (args.size < 2) {
                    addTranslatedChatLine("zombiesaddon.debug.isNotZombies.info")
                    addChat("isNotZombies: ${isNotZombies.withColor()}")
                    return
                }
                isNotZombies = when (args[1]) {
                    "false" -> false
                    "true" -> true
                    else -> throw WrongUsageException("/za_debug isNotZombies [false|true]")
                }
                addTranslatedChat("zombiesaddon.debug.setFlag", "isNotZombies", isNotZombies.withColor())
            }
            "serverNumber" -> {
                if (args.size < 2) {
                    addTranslatedChatLine("zombiesaddon.debug.serverNumber.info")
                    addChat("${getTranslatedString("zombiesaddon.debug.serverNumber")}: ${serverNumber.withColor()}")
                    return
                }
                serverNumber = if (args[1] == "null") null else ServerNumber(args[1])
                addTranslatedChat("zombiesaddon.debug.setFlag", getTranslatedString("zombiesaddon.debug.serverNumber"), serverNumber.withColor())
            }
            "gameMode" -> {
                if (args.size < 2) {
                    addChat("${getTranslatedString("zombiesaddon.debug.gameMode")}: $gameMode")
                    return
                }
                val map = when (args[1]) {
                    "de" -> ZombiesMap.DEAD_END
                    "bb" -> ZombiesMap.BAD_BLOOD
                    "aa" -> ZombiesMap.ALIEN_ARCADIUM
                    "pr" -> ZombiesMap.PRISON
                    else -> throw WrongUsageException("/za_debug gameMode [de|bb|aa|pr] [normal|hard|rip]")
                }
                val difficulty = if (args.size < 3) Difficulty.NORMAL else when (args[2]) {
                    "normal" -> Difficulty.NORMAL
                    "hard" -> Difficulty.HARD
                    "rip" -> Difficulty.RIP
                    else -> throw WrongUsageException("/za_debug gameMode [de|bb|aa|pr] [normal|hard|rip]")
                }
                gameMode = map.getNormalGameMode().appliedDifficulty(difficulty)
                addTranslatedChat("zombiesaddon.debug.setFlag", getTranslatedString("zombiesaddon.debug.gameMode"), "§a${gameMode}")
                val game = GameManager.game ?: return
                game.gameMode = gameMode
            }
            "new" -> {
                runCatching { GameManager.splitOrNew(0, true) }.onSuccess {
                    addTranslatedChat("zombiesaddon.debug.new", gameMode, serverNumber!!)
                }.onFailure { addTranslatedChat("zombiesaddon.message.failed.splitOrNew", it.message ?: "알 수 없음(Unknown)") }
            }
            "pass" -> {
                val game = GameManager.game ?: run {
                    addTranslatedChat("zombiesaddon.debug.noGame")
                    return
                }
                if (args.size < 2) throw WrongUsageException("zombiesaddon.commands.za_debug.usage.pass")
                val round = args[1].toIntOrNull() ?: throw NumberInvalidException("commands.generic.num.invalid", args[1])
                if (round <= 0) throw NumberInvalidException("commands.generic.num.tooSmall", args[1], 1)
                game.pass(round)
                addTranslatedChat("zombiesaddon.debug.pass", round)
                DelayedTask(10) {
                    if (args.size >= 3) for (i in 2 until args.size) {
                        val player = args[i]
                        NotLast.debugPlayerList += player
                    }
                }
            }
            "helicopter" -> {
                val game = GameManager.game ?: run {
                    addTranslatedChat("zombiesaddon.debug.noGame")
                    return
                }
                if (game.gameMode.map != ZombiesMap.PRISON) {
                    addTranslatedChat("zombiesaddon.debug.helicopter.notPrison")
                    return
                }
                game.helicopter()
                addTranslatedChat("zombiesaddon.debug.helicopter.called")
            }
            "end" -> {
                val game = GameManager.game ?: run {
                    addTranslatedChat("zombiesaddon.debug.noGame")
                    return
                }
                if (args.size < 2) throw WrongUsageException("/za_debug end <win|lose>")
                val isWin = when (args[1]) {
                    "win" -> true
                    "lose" -> false
                    else -> throw WrongUsageException("/za_debug end <win|lose>")
                }
                GameManager.endGame(game.serverNumber, isWin)
                addTranslatedChat("zombiesaddon.debug.end", if (isWin) "§awin" else "§close")
            }
            "remove" -> {
                GameManager.removeGame()
                addTranslatedChat("zombiesaddon.debug.remove")
            }
            else -> throw WrongUsageException(getCommandUsage(null))
        }
    }
    override fun addTabCompletionOptions(sender: ICommandSender?, args: Array<String?>, pos: BlockPos?): List<String>? {
        if (!ZombiesAddon.instance.debug) return null
        return when (args.size) {
            1 -> getListOfStringsMatchingLastWord(args, "isNotZombies", "serverNumber", "gameMode", "new", "pass", "helicopter", "end", "remove")
            2 -> when (args[0]) {
                "isNotZombies" -> getListOfStringsMatchingLastWord(args, "false", "true")
                "serverNumber" -> getListOfStringsMatchingLastWord(args, "null")
                "gameMode" -> getListOfStringsMatchingLastWord(args, "de", "bb", "aa", "pr")
                "end" -> getListOfStringsMatchingLastWord(args, "win", "lose")
                else -> null
            }
            3 -> when (args[0]) {
                "gameMode" -> getListOfStringsMatchingLastWord(args, "normal", "hard", "rip")
                "pass" -> {
                    val players = mc.theWorld.playerEntities.map { it.name!! }
                    getListOfStringsMatchingLastWord(args, *players.toTypedArray())
                }
                else -> null
            }
            else -> null
        }
    }
}

private fun Boolean.withColor() = if (this) "§atrue" else "§cfalse"

private fun ServerNumber?.withColor() = this?.let { "§a$it" } ?: "§cnull"