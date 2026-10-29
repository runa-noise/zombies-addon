package kr.runanoise.zombiesaddon

import com.google.gson.GsonBuilder
import com.seosean.showspawntime.ShowSpawnTime
import kr.runanoise.zombiesaddon.commands.Commands
import kr.runanoise.zombiesaddon.config.ZAConfig
import kr.runanoise.zombiesaddon.data.CustomPlaySound
import kr.runanoise.zombiesaddon.data.CustomPlaySoundLoader
import kr.runanoise.zombiesaddon.events.LastClientTickEvent
import kr.runanoise.zombiesaddon.modules.*
import kr.runanoise.zombiesaddon.update.UpdateChecker
import kr.runanoise.zombiesaddon.update.UpdateCheckerHandler
import kr.runanoise.zombiesaddon.utils.mc
import net.minecraftforge.client.event.GuiScreenEvent
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.common.config.Configuration
import net.minecraftforge.fml.common.Loader
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import org.apache.logging.log4j.Logger
import java.io.File
import java.io.FileWriter
import java.time.LocalDateTime

const val MODID = "zombiesaddon"
const val NAME = "Zombies Addon"
const val VERSION = "4.9.0-beta1"
const val SST_VERSION = "2.1.1"
const val ZU_VERSION = "1.4.4"

@Mod(modid = MODID, name = NAME, version = VERSION, guiFactory = "kr.runanoise.zombiesaddon.config.ZAGuiFactory", clientSideOnly = true)
class ZombiesAddon {
    companion object {
        @JvmStatic
        lateinit var instance: ZombiesAddon
            private set
    }
    init {
        instance = this
    }
    lateinit var logger: Logger
        private set

    var hasSST = false
    var hasZombiesUtils = false
    var hasFeather = false
    var debug = false
        internal set

    @Mod.EventHandler
    fun preInit(event: FMLPreInitializationEvent) {
        logger = event.modLog
        logger.info("preInit 시작.")
        File(mc.mcDataDir, "mods/deleter_zombiesaddon.bat").delete()
        val directory = File(event.modConfigurationDirectory, MODID)
        if (!directory.exists()) directory.mkdir()

        ZAConfig.init(Configuration(File(directory, "$MODID.cfg")))
        writeFile(directory)
        CustomPlaySoundLoader.loadFile()
    }

    @Mod.EventHandler
    fun init(@Suppress("unused") event: FMLInitializationEvent) {
        logger.info("init 시작.")
        registerAll()
    }

    @Mod.EventHandler
    fun postInit(@Suppress("unused") event: FMLPostInitializationEvent) {
        logger.info("postInit 시작.")
        removeSegFile()

        if (Loader.isModLoaded("showspawntime")) {
            hasSST = true
            runCatching { ShowSpawnTime.getMainConfiguration().ConfigLoad() }
            logger.info("ShowSpawnTime 모드가 감지되었습니다. $SST_VERSION 버전이 아닐 경우, 문제가 발생할 수 있습니다.")
        }
        if (Loader.isModLoaded("zombiesutils")) {
            hasZombiesUtils = true
            logger.info("Zombies Utils 모드가 감지되었습니다. $ZU_VERSION 버전이 아닐 경우, 문제가 발생할 수 있습니다.")
        }
        if (Loader.isModLoaded("feather")) {
            hasFeather = true
            logger.info("Feather Client가 감지되었습니다. Better Zombies Left가 작동하지 않으며, 기타 호환성 문제가 발생할 수 있습니다.")
        }

        logger.info("$NAME v${VERSION}이 로드되었습니다.")
    }

    @SubscribeEvent
    fun startGame(@Suppress("unused") event: GuiScreenEvent.DrawScreenEvent.Post) {
        logger.info("게임 시작.")
        MinecraftForge.EVENT_BUS.unregister(this)

        UpdateChecker.initAndCheck()
    }

    private fun writeFile(file: File) {
        javaClass.classLoader.getResourceAsStream("assets/$MODID/data/text/Custom Play Sound Guide.txt")!!.use { input ->
            File(file, "Custom Play Sound Guide.txt").outputStream().use { output ->
                val buffer = ByteArray(1024)
                var bytesRead: Int
                while (input.read(buffer).also { bytesRead = it } != -1) output.write(buffer, 0, bytesRead)
            }
        }

        javaClass.classLoader.getResourceAsStream("assets/$MODID/data/text/커스텀 소리 재생 가이드.txt")!!.use { input ->
            File(file, "커스텀 소리 재생 가이드.txt").outputStream().use { output ->
                val buffer = ByteArray(1024)
                var bytesRead: Int
                while (input.read(buffer).also { bytesRead = it } != -1) output.write(buffer, 0, bytesRead)
            }
        }

        val json = File(file, "CustomPlaySound.json")
        if (json.exists()) {
            logger.info("CustomPlaySound.json 파일이 존재합니다. 발생 가능한 문제 해결을 위해, 하위 버전의 문법을 현재 문법으로 변경합니다. playWave -> playType")
            json.writeText(json.readText().replace("playWave", "playType"))
            return
        }

        val cps = arrayOf(
            CustomPlaySound("note.pling", 2.0f, 0, 1),
            CustomPlaySound("note.pling", 1.5f, -60, 2),
            CustomPlaySound("note.pling", 1.5f, -40, 2),
            CustomPlaySound("note.pling", 1.5f, -20, 2),
            CustomPlaySound("random.orb", 0.5f, 0, 2)
        )

        val gson = GsonBuilder().setPrettyPrinting().create()
        val jsonString = gson.toJson(cps)

        FileWriter(json).use { it.write(jsonString) }
    }

    private fun removeSegFile() {
        val cutoff = LocalDateTime.now().minusDays(7)
        val pattern = Regex("""(\d{4})-(\d{2})-(\d{2})_(\d{2})-(\d{2})-(\d{2})_.+\.seg""")
        val folder = File(mc.mcDataDir, "zombies/runs")
        var count = 0

        folder.listFiles { it.isFile && it.name.endsWith(".seg") }?.forEach { file ->
            val matchResult = pattern.matchEntire(file.name) ?: return@forEach
            val (year, month, day, hour, minute, second) = matchResult.destructured
            val fileDateTime = runCatching {
                LocalDateTime.of(year.toInt(), month.toInt(), day.toInt(), hour.toInt(), minute.toInt(), second.toInt())
            }.getOrNull() ?: return@forEach
            if (fileDateTime.isBefore(cutoff)) {
                count++
                file.delete()
            }
        }
        logger.info("7일 이상 지난 .seg 파일 ${count}개를 삭제했습니다.")
    }

    private fun registerAll() {
        KeyBindings.registerAll()
        System.registerAll()

        Commands.registerCommands()

        MinecraftForge.EVENT_BUS.register(this)
        MinecraftForge.EVENT_BUS.register(ZAConfig)
        MinecraftForge.EVENT_BUS.register(UpdateCheckerHandler)
        MinecraftForge.EVENT_BUS.register(LastClientTickEvent.EventBridge)
        MinecraftForge.EVENT_BUS.register(ModuleListener)

        ModuleListener.registerModule(PlayerVisibility)
        ModuleListener.registerModule(BlockAlarm)
        ModuleListener.registerModule(NotLast)
        ModuleListener.registerModule(InternalTimer)
        ModuleListener.registerModule(WaveDelays)
        ModuleListener.registerModule(ZombiesStratViewer)
        ModuleListener.registerModule(SlaHandler)
        ModuleListener.registerModule(AutoRejoin)
        ModuleListener.registerModule(PowerupPatterns)
        ModuleListener.registerModule(LastWeapons)
        ModuleListener.registerModule(TextMacro)
        ModuleListener.registerModule(Patcher)
        ModuleListener.registerModule(BetterZombiesLeft)
    }
}