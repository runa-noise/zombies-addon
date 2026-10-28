package kr.runanoise.zombiesaddon.events

import kr.runanoise.zombiesaddon.game.Game
import net.minecraftforge.fml.common.eventhandler.Event

class GameRemoveEvent(val game: Game): Event()