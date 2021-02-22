package com.peterlaurence.book.pawk.introduction.classes

data class Point(var x: Int, var y: Int? = 3)

fun main() {
    val p1 = Point(1, null)
    val p2 = Point(1, null)
    println("Are points equals: ${p1 == p2}")
}