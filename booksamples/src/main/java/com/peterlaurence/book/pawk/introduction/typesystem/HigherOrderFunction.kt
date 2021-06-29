package com.peterlaurence.book.pawk.introduction.typesystem

fun getCurve(
    surface: (Double, Double) -> Int,
    x: Double
): (Double) -> Int {
    return { y -> surface(x, y) }
}