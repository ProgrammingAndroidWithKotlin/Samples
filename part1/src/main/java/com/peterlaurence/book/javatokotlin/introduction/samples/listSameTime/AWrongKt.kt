package com.peterlaurence.book.javatokotlin.introduction.samples.listSameTime

import java.util.*
import java.util.Arrays.asList
import java.util.concurrent.CopyOnWriteArrayList


class AWrongKt {
    var list =
        Collections.synchronizedList<Int>(object : ArrayList<Int?>() {
            init {
                add(1)
            }
        })

    fun add() {
        val last = list[list.size - 1]
        list.add(last + 1)
    }
}