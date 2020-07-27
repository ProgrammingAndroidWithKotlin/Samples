package com.peterlaurence.book.javatokotlin.datastreams

import java.time.LocalDateTime

data class Serie(val points: List<Point>, val charac: Charac)

data class Charac(val name: String, val type: CharacType)

enum class CharacType {
    CRITICAL,
    IMPORTANT,
    REGULAR
}

data class Point(val sn: String, val date: LocalDateTime, val value: Double)



