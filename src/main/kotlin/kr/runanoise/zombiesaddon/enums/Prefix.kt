package kr.runanoise.zombiesaddon.enums

@Suppress("unused")
enum class Prefix(val color: Int, val fadedColor: Int, val prefix: String) {
    BOSS(0xCC5555, 0x7A3333, "B"),
    BLAZES(0xEFB61F, 0x8F6D0F, "BL"),
    SLIME(0x88FF88, 0x51A951, "S"),
    HBM(0x2A415F, 0x193241, "HBM"),
    WITHER_SKELETON(0x888888, 0x515151, "WS"),
    OLD_ONE(0x55AA55, 0x336633, "O1"),
    GIANT(0x00FFFF, 0x009999, "G"),
    POLICE(0x16537E, 0x0E324D, "P"),
    CELL(0xFF8234, 0x99501F, "C"),
    WINDOW(0xAAAAAA, 0x666666, "W")
}