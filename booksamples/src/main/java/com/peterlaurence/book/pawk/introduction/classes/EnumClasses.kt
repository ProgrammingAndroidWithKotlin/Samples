package com.peterlaurence.book.pawk.introduction.classes

interface Signal {
    fun switch(): STOPLIGHT
}

enum class STOPLIGHT : Signal {
    RED {
        override fun switch() = GREEN
    },
    YELLOW {
        override fun switch() = RED
    },
    GREEN {
        override fun switch() = YELLOW
    }
}

fun main() {
    val light = STOPLIGHT.GREEN

    /* When as statement */
    when (light) {
        STOPLIGHT.RED -> println("red")
        STOPLIGHT.GREEN -> println("green")
    }

    /* When as expression */
    val x = when (light) {
        STOPLIGHT.RED -> 0
        STOPLIGHT.GREEN -> 1
        STOPLIGHT.YELLOW -> 2
    }

    /* When as statement, forced to be exhaustive */
    when (light) {
        STOPLIGHT.RED -> println("red")
        STOPLIGHT.GREEN -> println("green")
        STOPLIGHT.YELLOW -> println("yellow")
    }.exhaustive
}

private val <T> T.exhaustive: T
    get() = this