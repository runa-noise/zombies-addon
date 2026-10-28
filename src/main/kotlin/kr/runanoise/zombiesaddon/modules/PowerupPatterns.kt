package kr.runanoise.zombiesaddon.modules

import kr.runanoise.zombiesaddon.KeyBindings
import kr.runanoise.zombiesaddon.config.ZAConfig
import kr.runanoise.zombiesaddon.data.ServerNumber
import kr.runanoise.zombiesaddon.enums.ZombiesMap
import kr.runanoise.zombiesaddon.events.*
import kr.runanoise.zombiesaddon.game.GameManager
import kr.runanoise.zombiesaddon.utils.*
import net.minecraft.entity.item.EntityArmorStand
import net.minecraft.entity.player.EntityPlayer
import net.minecraftforge.client.event.ClientChatReceivedEvent
import net.minecraftforge.client.event.RenderGameOverlayEvent
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.eventhandler.Event
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.InputEvent

object PowerupPatterns: Module("Powerup Patterns") {
    private val insPatternArr1 = intArrayOf(2, 5, 8, 11, 14, 17, 20, 23)
    private val insPatternArr2 = intArrayOf(3, 6, 9, 12, 15, 18, 21, 24)

    private val maxPatternArr1 = intArrayOf(2, 5, 8, 12, 16, 21, 26, 31, 36, 41, 46, 51, 61, 66, 71, 76, 81, 86, 91, 96)
    private val maxPatternArr2 = intArrayOf(3, 6, 9, 13, 17, 22, 27, 32, 37, 42, 47, 52, 62, 67, 72, 77, 82, 87, 92, 97, 102)

    private val ssPatternArr1 = intArrayOf(5, 15, 45, 55, 65, 75, 85, 95)
    private val ssPatternArr2 = intArrayOf(6, 16, 26, 36, 46, 66, 76, 86, 96)
    private val ssPatternArr3 = intArrayOf(7, 17, 27, 37, 47, 67, 77, 87, 97)

    val fieldsStorage = mutableMapOf<ServerNumber, FieldsStorage>()

    val fields get() = fieldsStorage[getServerNumber()]

    override fun onRoundStart(event: RoundStartEvent) {
        val serverNumber = event.game.serverNumber
        if (fieldsStorage[serverNumber] == null) fieldsStorage[serverNumber] = FieldsStorage()
        val fields = fieldsStorage[serverNumber] ?: return

        if (event.game.round == 1) {
            fields.spawnedEntities.clear()
            fields.queuedInsPattern = 0
            fields.queuedMaxPattern = 0
            fields.queuedSsPattern = 0
        }

        fields.insPattern = fields.queuedInsPattern
        fields.maxPattern = fields.queuedMaxPattern
        fields.ssPattern = fields.queuedSsPattern
    }

    override fun onRender(event: RenderGameOverlayEvent.Text) {
        if (isNotPlayZombies()) return
        val game = GameManager.game ?: return
        val fields = fieldsStorage[game.serverNumber] ?: return
        drawTimers(fields)
        drawPatterns(fields)
    }

    private fun drawTimers(fields: FieldsStorage) {
        if (fields.insTimer) {
            val timer = ManualTimer.ins.timer
            val second = timer / 20
            val timerText = String.format("%02d", second)

            val str = "${getTranslatedString("zombiesaddon.game.ins")}: §f${timerText}s"
            fr.drawStringWithShadow(str, HudUtils.getPowerupPatternsStrX(str), HudUtils.getPowerupPatternsStrY(1), 0xffffff)
            if (timer <= 0) fields.insTimer = false
        }
        if (fields.maxTimer) {
            val timer = ManualTimer.max.timer
            val second = timer / 20
            val timerText = String.format("%02d", second)

            val str = "${getTranslatedString("zombiesaddon.game.max")}: §f${timerText}s"
            fr.drawStringWithShadow(str, HudUtils.getPowerupPatternsStrX(str), HudUtils.getPowerupPatternsStrY(2), 0xffffff)
            if (timer <= 0) fields.maxTimer = false
        }
        if (fields.ssTimer) {
            val timer = ManualTimer.ss.timer
            val second = timer / 20
            val timerText = String.format("%02d", second)

            val str = "${getTranslatedString("zombiesaddon.game.ss")}: §f${timerText}s"
            fr.drawStringWithShadow(str, HudUtils.getPowerupPatternsStrX(str), HudUtils.getPowerupPatternsStrY(3), 0xffffff)
            if (timer <= 0) fields.ssTimer = false
        }
        if (fields.dgTimer) {
            val timer = ManualTimer.dg.timer
            val second = timer / 20
            val timerText = String.format("%02d", second)

            val str = "${getTranslatedString("zombiesaddon.game.dg")}: §f${timerText}s"
            fr.drawStringWithShadow(str, HudUtils.getPowerupPatternsStrX(str), HudUtils.getPowerupPatternsStrY(4), 0xffffff)
            if (timer <= 0) fields.dgTimer = false
        }
        if (fields.carTimer) {
            val timer = ManualTimer.car.timer
            val second = timer / 20
            val timerText = String.format("%02d", second)

            val str = "${getTranslatedString("zombiesaddon.game.car")}: §f${timerText}s"
            fr.drawStringWithShadow(str, HudUtils.getPowerupPatternsStrX(str), HudUtils.getPowerupPatternsStrY(5), 0xffffff)
            if (timer <= 0) fields.carTimer = false
        }
        if (fields.bgTimer) {
            val timer = ManualTimer.bg.timer
            val second = timer / 20
            val timerText = String.format("%02d", second)

            val str = "${getTranslatedString("zombiesaddon.game.bg")}: §f${timerText}s"
            fr.drawStringWithShadow(str, HudUtils.getPowerupPatternsStrX(str), HudUtils.getPowerupPatternsStrY(6), 0xffffff)
            if (timer <= 0) fields.bgTimer = false
        }
    }

    private fun drawPatterns(fields: FieldsStorage) {
        val game = GameManager.game ?: return
        val round = if (game.gameEnd) 0 else game.round
        val map = game.gameMode.map

        if (fields.insPattern != 0) {
            var patternRound = 0
            if (fields.insPattern == 2) {
                for (i in insPatternArr1) if (round <= i) {
                    if (map == ZombiesMap.DEAD_END && i == 5) {
                        patternRound = 8
                        break
                    }
                    if (map != ZombiesMap.ALIEN_ARCADIUM && i == 20) {
                        patternRound = 23
                        break
                    }
                    patternRound = i
                    break
                }
                if (patternRound != 0) drawIns(fields, patternRound)
            } else if (fields.insPattern == 3) {
                for (i in insPatternArr2) if (round <= i) {
                    if (map != ZombiesMap.ALIEN_ARCADIUM && i == 15) {
                        patternRound = 18
                        break
                    }
                    if (map != ZombiesMap.ALIEN_ARCADIUM || i != 24) patternRound = i
                    break
                }
                if (patternRound != 0) drawIns(fields, patternRound)
            }
        }

        if (fields.maxPattern != 0) {
            var patternRound = 0
            if (fields.maxPattern == 2) {
                for (i in maxPatternArr1) if (round <= i) {
                    if (map == ZombiesMap.DEAD_END && i == 5) {
                        patternRound = 8
                        break
                    }
                    if (map != ZombiesMap.ALIEN_ARCADIUM && i > 30) break
                    patternRound = i
                    break
                }
                if (patternRound != 0) drawMax(fields, patternRound)
            } else if (fields.maxPattern == 3) {
                for (i in maxPatternArr2) if (round <= i) {
                    if (map != ZombiesMap.ALIEN_ARCADIUM && i > 30) break
                    patternRound = i
                    break
                }
                if (patternRound != 0) drawMax(fields, patternRound)
            }
        }

        if (fields.ssPattern != 0) {
            var patternRound = 0
            if (fields.ssPattern == 5) {
                for (i in ssPatternArr1) if (round <= i) {
                    patternRound = i
                    break
                }
                if (patternRound != 0) drawSs(fields, patternRound)
            } else if (fields.ssPattern == 6) {
                for (i in ssPatternArr2) if (round <= i) {
                    patternRound = i
                    break
                }
                if (patternRound != 0) drawSs(fields, patternRound)
            } else if (fields.ssPattern == 7) {
                for (i in ssPatternArr3) if (round <= i) {
                    patternRound = i
                    break
                }
                if (patternRound != 0) drawSs(fields, patternRound)
            }
        }
    }

    override fun onLastTick(event: LastClientTickEvent) {
        if (isNotPlayZombies()) return
        val game = GameManager.game ?: return
        if (game.gameEnd) return
        val fields = fieldsStorage[game.serverNumber] ?: return
        val round = game.round

        for (entity in mc.theWorld.loadedEntityList) {
            if (entity !is EntityArmorStand) continue
            if (entity in fields.spawnedEntities) continue


            val name = entity.name.withoutColor()

            when (name) {
                in ins -> {
                    @Suppress("DuplicatedCode")
                    fields.spawnedEntities += entity
                    for (i in insPatternArr1) if (i == round) {
                        fields.queuedInsPattern = 2
                        break
                    }
                    for (i in insPatternArr2) if (i == round) {
                        fields.queuedInsPattern = 3
                        break
                    }
                }
                in max -> {
                    fields.spawnedEntities += entity
                    for (i in maxPatternArr1) if (i == round) {
                        fields.queuedMaxPattern = 2
                        break
                    }
                    for (i in maxPatternArr2) if (i == round) {
                        fields.queuedMaxPattern = 3
                        break
                    }
                }
                in ss -> {
                    fields.spawnedEntities += entity
                    for (i in ssPatternArr1) if (i == round) {
                        fields.queuedSsPattern = 5
                        break
                    }
                    for (i in ssPatternArr2) if (i == round) {
                        fields.queuedSsPattern = 6
                        break
                    }
                    for (i in ssPatternArr3) if (i == round) {
                        fields.queuedSsPattern = 7
                        break
                    }
                }
                in dg -> fields.spawnedEntities += entity
                in car -> fields.spawnedEntities += entity
                in bg -> fields.spawnedEntities += entity
            }
        }
    }

    private fun drawIns(fields: FieldsStorage, pattern: Int) {
        if (fields.insTimer) return
        val str = "${getTranslatedString("zombiesaddon.game.ins")}§f: ${getTranslatedString("zombiesaddon.game.round", true, pattern)}"
        fr.drawStringWithShadow(str, HudUtils.getPowerupPatternsStrX(str), HudUtils.getPowerupPatternsStrY(1), 0xffffff)
    }

    private fun drawMax(fields: FieldsStorage, pattern: Int) {
        if (fields.maxTimer) return
        val str = "${getTranslatedString("zombiesaddon.game.max")}§f: ${getTranslatedString("zombiesaddon.game.round", true, pattern)}"
        fr.drawStringWithShadow(str, HudUtils.getPowerupPatternsStrX(str), HudUtils.getPowerupPatternsStrY(2), 0xffffff)
    }

    private fun drawSs(fields: FieldsStorage, pattern: Int) {
        if (fields.ssTimer) return
        val str = "${getTranslatedString("zombiesaddon.game.ss")}§f: ${getTranslatedString("zombiesaddon.game.round", true, pattern)}"
        fr.drawStringWithShadow(str, HudUtils.getPowerupPatternsStrX(str), HudUtils.getPowerupPatternsStrY(3), 0xffffff)
    }

    override fun onGameEnd(event: GameEndEvent) {
        val fields = fieldsStorage[event.game.serverNumber] ?: return
        fields.insTimer = false
        fields.maxTimer = false
        fields.ssTimer = false
        fields.dgTimer = false
        fields.carTimer = false
        fields.bgTimer = false
    }

    override fun onKeyInput(event: InputEvent.KeyInputEvent) {
        if (isDisable() || !isEnable() || isNotPlayZombies()) {
            resetKeys()
            return
        }
        val fields = fieldsStorage[GameManager.game?.serverNumber] ?: return

        if (KeyBindings.insTimer.isPressed) fields.insTimer = true
        if (KeyBindings.maxTimer.isPressed) fields.maxTimer = true
        if (KeyBindings.ssTimer.isPressed) fields.ssTimer = true
        if (KeyBindings.dgTimer.isPressed) fields.dgTimer = true
        if (KeyBindings.carTimer.isPressed) fields.carTimer = true
        if (KeyBindings.bgTimer.isPressed) fields.bgTimer = true
        if (KeyBindings.autoTimer.isPressed) autoTimer(fields)
    }

    private fun resetKeys() {
        KeyBindings.insTimer.isPressed
        KeyBindings.maxTimer.isPressed
        KeyBindings.ssTimer.isPressed
        KeyBindings.dgTimer.isPressed
        KeyBindings.carTimer.isPressed
        KeyBindings.bgTimer.isPressed
        KeyBindings.autoTimer.isPressed
    }

    private fun autoTimer(fields: FieldsStorage) {
        val entities = mc.thePlayer.rayTraceEntity()
        if (entities.isEmpty()) {
            addTranslatedChat("zombiesaddon.modules.powerupPatterns.autoTimer.failed")
            return
        }
        var findEntity = false

        for (entity in entities) {
            if (entity !in fields.spawnedEntities) continue
            val name = entity.name.withoutColor()
            when (name) {
                in ins -> {
                    fields.insTimer = true
                    findEntity = true
                }
                in max -> {
                    fields.maxTimer = true
                    findEntity = true
                }
                in ss -> {
                    fields.ssTimer = true
                    findEntity = true
                }
                in dg -> {
                    fields.dgTimer = true
                    findEntity = true
                }
                in car -> {
                    fields.carTimer = true
                    findEntity = true
                }
                in bg -> {
                    fields.bgTimer = true
                    findEntity = true
                }
            }
        }
        if (!findEntity) addTranslatedChat("zombiesaddon.modules.powerupPatterns.autoTimer.failed")
    }

    override fun onChat(event: ClientChatReceivedEvent) {
        if (isNotPlayZombies()) return
        val fields = fieldsStorage[GameManager.game?.serverNumber] ?: return
        val message = event.message.unformattedText.withoutColor()
        if (">" in message) return

        when {
            message inContains ins -> fields.insTimer = false
            message inContains max -> fields.maxTimer = false
            message inContains ss -> fields.ssTimer = false
            message inContains dg -> fields.dgTimer = false
            message inContains car -> fields.carTimer = false
            message inContains bg -> fields.bgTimer = false
        }
    }

    override fun onEvent(event: Event) {
        if (event !is GameRemoveEvent) return
        fieldsStorage.remove(event.game.serverNumber)
    }

    override fun isEnable() = ZAConfig.modules.enablePowerupPatterns

    class FieldsStorage {
        val spawnedEntities = mutableSetOf<EntityArmorStand>()
        var insPattern = 0
        var maxPattern = 0
        var ssPattern = 0
        var queuedInsPattern = 0
        var queuedMaxPattern = 0
        var queuedSsPattern = 0

        var insTimer = false
            set(b) {
                field = b
                if (field) {
                    ManualTimer.ins.runTimer()
                    addTranslatedChat("zombiesaddon.modules.powerupPatterns.timer", getTranslatedString("zombiesaddon.game.ins"))
                }
            }
        var maxTimer = false
            set(b) {
                field = b
                if (field) {
                    ManualTimer.max.runTimer()
                    addTranslatedChat("zombiesaddon.modules.powerupPatterns.timer", getTranslatedString("zombiesaddon.game.max"))
                }
            }
        var ssTimer = false
            set(b) {
                field = b
                if (field) {
                    ManualTimer.ss.runTimer()
                    addTranslatedChat("zombiesaddon.modules.powerupPatterns.timer", getTranslatedString("zombiesaddon.game.ss"))
                }
            }
        var dgTimer = false
            set(b) {
                field = b
                if (field) {
                    ManualTimer.dg.runTimer()
                    addTranslatedChat("zombiesaddon.modules.powerupPatterns.timer", getTranslatedString("zombiesaddon.game.dg"))
                }
            }
        var carTimer = false
            set(b) {
                field = b
                if (field) {
                    ManualTimer.car.runTimer()
                    addTranslatedChat("zombiesaddon.modules.powerupPatterns.timer", getTranslatedString("zombiesaddon.game.car"))
                }
            }
        var bgTimer = false
            set(b) {
                field = b
                if (field) {
                    ManualTimer.bg.runTimer()
                    addTranslatedChat("zombiesaddon.modules.powerupPatterns.timer", getTranslatedString("zombiesaddon.game.bg"))
                }
            }
    }
}

private class ManualTimer {
    companion object {
        val ins = ManualTimer()
        val max = ManualTimer()
        val ss = ManualTimer()
        val dg = ManualTimer()
        val car = ManualTimer()
        val bg = ManualTimer()
    }

    var timer = 1200

    fun runTimer() {
        timer = 1200
        MinecraftForge.EVENT_BUS.register(this)
    }

    @SubscribeEvent
    fun onTick(@Suppress("unused") event: ServerTickEvent) {
        if (--timer <= 0) MinecraftForge.EVENT_BUS.unregister(this)
    }
}

private fun EntityPlayer.rayTraceEntity(): List<EntityArmorStand> {
    val maxDistance = 30
    val startVec = this.getPositionEyes(1.0f)
    val lookVec = this.getLook(1.0f)
    val endVec = startVec.addVector(lookVec.xCoord * maxDistance, lookVec.yCoord * maxDistance, lookVec.zCoord * maxDistance)

    val aabb = this.entityBoundingBox.addCoord(lookVec.xCoord * maxDistance, lookVec.yCoord * maxDistance, lookVec.zCoord * maxDistance).expand(1.0, 1.0, 1.0)

    val entities = mc.theWorld.getEntitiesWithinAABB(EntityArmorStand::class.java, aabb)

    return entities.filter { it.entityBoundingBox.expand(0.8, 0.8, 0.8).calculateIntercept(startVec, endVec) != null }
}