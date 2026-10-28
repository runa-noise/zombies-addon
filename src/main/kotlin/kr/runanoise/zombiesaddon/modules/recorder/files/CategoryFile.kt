package kr.runanoise.zombiesaddon.modules.recorder.files

import kr.runanoise.zombiesaddon.enums.GameMode
import kr.runanoise.zombiesaddon.modules.recorder.Indexable
import kr.runanoise.zombiesaddon.modules.recorder.readOrCreate
import kr.runanoise.zombiesaddon.modules.recorder.writeData
import kr.runanoise.zombiesaddon.utils.addTranslatedChat
import kr.runanoise.zombiesaddon.utils.logger
import java.io.File

class CategoryFile(category: File, val gameMode: GameMode): File(category, "${gameMode.toStringForRecorder()}.times") {
    private val data = readOrCreate()

    val bestSegments = object: Indexable<Int> {
        override operator fun get(round: Int) = data.bestSegments[round - 1].toInt()
        override operator fun set(round: Int, ticks: Int) {
            data.bestSegments[round - 1] = ticks.toShort()

            runCatching { writeData(data) }.onFailure {
                logger.error("구간 최고 기록을 저장하는데 실패했습니다.", it)
                addTranslatedChat("zombiesaddon.modules.recorder.failed.save.bestSegment", it.message ?: "알 수 없음(Unknown)")
            }
        }
    }

    val personalBests = object: Indexable<Int> {
        override operator fun get(round: Int) = data.personalBests[round - 1]
        override operator fun set(round: Int, ticks: Int) {
            data.personalBests[round - 1] = ticks

            runCatching { writeData(data) }.onFailure {
                logger.error("개인 최고 기록을 저장하는데 실패했습니다.", it)
                addTranslatedChat("zombiesaddon.modules.recorder.failed.save.pb", it.message ?: "알 수 없음(Unknown)")
            }
        }
    }
}