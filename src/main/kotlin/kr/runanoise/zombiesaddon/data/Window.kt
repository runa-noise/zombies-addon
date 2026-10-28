package kr.runanoise.zombiesaddon.data

class Window(val id: Int, private var x: Short, private var y: Short, private var z: Short) {
    var active = false
    val xyz get() = intArrayOf(x.toInt(), y.toInt(), z.toInt())

    fun rotate(rotations: Int) {
        when (rotations) {
            1 -> rotateCounterClockwise()
            2 -> mirrorBoth()
            3 -> rotateClockwise()
        }
    }

    fun mirrorX() {
        x = (-x).toShort()
    }

    fun mirrorZ() {
        z = (-z).toShort()
    }

    private fun rotateCounterClockwise() {
        val temp = x
        x = (-z).toShort()
        z = temp
    }

    private fun mirrorBoth() {
        x = (-x).toShort()
        z = (-z).toShort()
    }

    private fun rotateClockwise() {
        val temp = x
        x = z
        z = (-temp).toShort()
    }
}