package com.peterlaurence.book.pawk.introduction.typesystem

import kotlin.math.pow

val func: (Double) -> Double = { 2.0.pow(it) }
val result = func(4.0)
