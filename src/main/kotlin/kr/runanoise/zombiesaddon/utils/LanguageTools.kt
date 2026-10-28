package kr.runanoise.zombiesaddon.utils

val heal = arrayOf("Heal Skill", "회복 기술", "治療スキル")
val lrod = arrayOf("Lightning Rod Skill", "번개 막대 기술", "稲妻の棒のスキル")
val turret = arrayOf("Deployable Turret Skill")

val ins = arrayOf("INSTA KILL", "Insta Kill", "즉시 처치", "インスタキル")
val max = arrayOf("MAX AMMO", "Max Ammo", "탄약 충전", "탄약 보급", "マックスアモ")
val ss = arrayOf("SHOPPING SPREE", "Shopping Spree", "지름신 강림", "ショッピングスプリー")
val dg = arrayOf("DOUBLE GOLD", "Double Gold", "더블 골드", "ダブルゴールド")
val car = arrayOf("CARPENTER", "Carpenter", "카펜터", "목수", "カーペンター")
val bg = arrayOf("BONUS GOLD", "Bonus Gold", "보너스 골드", "ボーナスゴールド")

val rev = arrayOf("REVIVE", "부활", "気絶")
val dead = arrayOf("DEAD", "사망", "死亡")
val quit = arrayOf("QUIT", "떠남", "退出")

val win = arrayOf("You Win!", "승리했습니다!", "勝利！")
val lose = arrayOf("Game Over!", "게임 끝!", "ゲームオーバー！")

val eh = arrayOf("Extra Health", "추가 체력", "最大体力増加")
val postUlt = arrayOf("Ultimate", "アルチィメット")
val preUlt = arrayOf("레벨")

val zombiesLeft = arrayOf("Zombies Left", "남은 좀비", "残りゾンビ")


fun getLevel(itemName: String): Int {
    var name = ""
    if (itemName inContains postUlt) for (it in postUlt) {
        if (it in itemName) {
            name = runCatching { itemName.split(it)[1].trim() }.getOrElse { return 0 }
            break
        }
    } else if (itemName inContains preUlt) for (it in preUlt) {
        if (it in itemName) {
            name = runCatching { itemName.split(it)[0].trim() }.getOrElse { return 0 }
            break
        }
    } else if (itemName inContains eh) for (it in eh) {
        if (it in itemName) {
            name = runCatching { itemName.split(it)[1].trim() }.getOrElse { return 0 }
            break
        }
    }

    return when (name) {
        "I" -> 1
        "II" -> 2
        "III" -> 3
        "IV" -> 4
        "V" -> 5
        "VI" -> 6
        "VII" -> 7
        "VIII" -> 8
        "IX" -> 9
        "X" -> 10
        else -> 0
    }
}

infix fun String.inContains(arr: Array<String>) = arr.any { it in this }