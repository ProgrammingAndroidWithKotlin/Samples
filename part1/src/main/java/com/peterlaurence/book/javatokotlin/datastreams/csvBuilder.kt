package com.peterlaurence.book.javatokotlin.datastreams

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


/**
 * Create CSV string of the data with the followings columns:
 * date | sn | charac1 | charac2 | .. | characN
 */
private fun createCsvAlt1(series: List<Serie>): String {
    data class CharacSerialKey(val serial: String, val charac: Charac, val date: LocalDateTime)

    val valuesMap = hashMapOf<CharacSerialKey, Point>()

    series.forEach { serie ->
        serie.points.forEach { point ->
            val key = CharacSerialKey(point.serial, serie.charac, point.date)
            valuesMap[key] = point
        }
    }

    val distinctCharacs =
        valuesMap.keys.distinctBy { it.charac }.map { it.charac }.sortedBy { it.name }
    val distinctDates = valuesMap.values.distinctBy { it.date }.map { it.date }.sorted()
    val distinctSerials = valuesMap.keys.distinctBy { it.serial }.map { it.serial }

    val csvHeader = "date;serial;" + distinctCharacs.joinToString(";") { it.name } + "\n"

    val rows = distinctDates.joinToString("") { date ->
        distinctSerials.map { serial ->
            val characColumns = distinctCharacs.map { charac ->
                val value = valuesMap[CharacSerialKey(serial, charac, date)]
                value?.value?.toString() ?: ""
            }

            listOf(date.format(), serial) + characColumns
        }.joinToString(separator = "") {
            it.joinToString(";", postfix = "\n")
        }
    }

    return csvHeader + rows
}

/**
 * Create CSV string of the data with the followings columns:
 * date | sn | charac1 | charac2 | .. | characN
 */
private fun createCsvAlt2(series: List<Serie>): String {
    data class CharacSerialKey(val serial: String, val charac: Charac, val date: LocalDateTime)

    val valuesMap = hashMapOf<CharacSerialKey, Point>()

    series.filter {
        it.charac.type == CharacType.CRITICAL || it.charac.type == CharacType.IMPORTANT
    }.forEach { serie ->
        serie.points.forEach { point ->
            val key = CharacSerialKey(point.serial, serie.charac, point.date)
            valuesMap[key] = point
        }
    }

    val distinctCharacs = valuesMap.keys.distinctBy { it.charac }.map { it.charac }
    val distinctDates = valuesMap.values.distinctBy { it.date }.map { it.date }.sorted()
    val distinctSerials = valuesMap.keys.distinctBy { it.serial }.map { it.serial }

    val csvHeader = "date;serial;" + distinctCharacs.joinToString(";") { it.name } + "\n"

    val rows = distinctDates.joinToString("") { date ->
        distinctSerials.mapNotNull { serial ->
            val characColumns = distinctCharacs.map { charac ->
                val value = valuesMap[CharacSerialKey(serial, charac, date)]
                value?.value?.toString() ?: ""
            }

            if (characColumns.any { it.isNotEmpty() }) {
                listOf(date.format(), serial) + characColumns
            } else null
        }.joinToString(separator = "") {
            it.joinToString(";", postfix = "\n")
        }
    }

    return csvHeader + rows
}

private fun createCsv(series: List<Serie>): String {
    data class PointAndCharac(val point: Point, val charac: Charac)

    val pointAndCharacList = series.map { serie ->
        serie.points.map { point ->
            PointAndCharac(point, serie.charac)
        }
    }.flatten()

    val distinctCharacs =
        pointAndCharacList.distinctBy { it.charac }.map { it.charac }.sortedBy { it.name }
    val csvHeader = "date;serial;" + distinctCharacs.joinToString(";") { it.name } + "\n"

    val rows = pointAndCharacList.groupBy { it.point.date }.toSortedMap().map { (date, list) ->
        val bySerial = list.groupBy { it.point.serial }

        bySerial.map { (serial, list) ->
            val characColumns = distinctCharacs.map { charac ->
                val value = list.firstOrNull { it.charac == charac }
                value?.point?.value?.toString() ?: ""
            }

            listOf(date.format(), serial) + characColumns
        }.joinToString(separator = "") {
            it.joinToString(separator = ";", postfix = "\n")
        }
    }.joinToString(separator = "")

    return csvHeader + rows
}

private fun LocalDateTime.format(): String {
    return this.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))
}

fun main() {
    val datesOrig = listOf<LocalDateTime>(
        LocalDateTime.parse("2020-07-27T15:45:00"),
        LocalDateTime.parse("2020-07-27T15:35:00"),
        LocalDateTime.parse("2020-07-27T15:25:00"),
        LocalDateTime.parse("2020-07-27T15:15:00")
    )

    val dates = listOf<LocalDateTime>(
        LocalDateTime.parse("2020-07-27T15:45:00"),
        LocalDateTime.parse("2020-07-27T15:35:00"),
        LocalDateTime.parse("2020-07-27T15:25:00"),
        LocalDateTime.parse("2020-07-27T15:15:00"),
        LocalDateTime.parse("2020-07-27T15:10:00")
    )

    val seriesExample = listOf(
        Serie(
            points = listOf(
                Point("HC11", dates[0], 15.1),
                Point("HC12", dates[1], 15.05),
                Point("HC13", dates[2], 15.11),
                Point("HC14", dates[3], 15.08),
                Point("HC11", dates[3], 15.06)
            ),
            charac = Charac("AngleOfAttack", CharacType.CRITICAL)
        ),
        Serie(
            points = listOf(
                Point("HC11", dates[0], 0.68),
                Point("HC12", dates[1], 0.7),
                Point("HC13", dates[2], 0.69),
                Point("HC14", dates[3], 0.71)
            ),
            charac = Charac("ChordLength", CharacType.IMPORTANT)
        ),
        Serie(
            points = listOf(
                Point("HC11", dates[0], "#2196F3"),
                Point("HC14", dates[3], "#795548")
            ),
            charac = Charac("PaintColor", CharacType.REGULAR)
        )
    )

    val csv = createCsv(seriesExample)
    println(csv)
}