package com.peterlaurence.book.pawk.datastreams

import java.time.LocalDateTime

data class TimeSerie(val points: List<Point>, val charac: Charac)

data class Charac(val name: String, val type: CharacType)

enum class CharacType {
    CRITICAL,
    IMPORTANT,
    REGULAR
}

data class Point(val serial: String, val date: LocalDateTime, val value: Double)



