package kr.runanoise.zombiesaddon.data

import kr.runanoise.zombiesaddon.enums.Prefix

class Round(val waves: Array<Wave>)

class Wave(val ticks: Int, val prefixes: Array<Prefix>, val mobCount: Int) {
    @Suppress("unused")
    constructor(ticks: Int) : this(ticks, emptyArray(), 0)
}

@JvmInline
value class ServerNumber(private val value: String) {
    override fun toString() = value
}