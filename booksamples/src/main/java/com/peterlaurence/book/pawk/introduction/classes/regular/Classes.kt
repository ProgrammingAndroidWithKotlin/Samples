package com.peterlaurence.book.pawk.introduction.classes.regular

import kotlin.math.pow
import kotlin.math.sqrt


class Point(val x: Int, val y: Int)

class Segment(x1: Int, y1: Int, x2: Int, y2: Int) {
    val length: Double = sqrt(
        (x2 - x1).toDouble().pow(2.0) +
                (y2 - y1).toDouble().pow(2.0)
    )

    // will execute after point initialization
    init {
        println("Point created with length $length")
    }

    constructor(x1: Int, y1: Int, pt2: Point) :
            this(x1, y1, x2 = pt2.x, y2 = pt2.y)

    constructor(pt1: Point, pt2: Point) :
            this(x1 = pt1.x, y1 = pt1.y, x2 = pt2.x, y2 = pt2.y)

}