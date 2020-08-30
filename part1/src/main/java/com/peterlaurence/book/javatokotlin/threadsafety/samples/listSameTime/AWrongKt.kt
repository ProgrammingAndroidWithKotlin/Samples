package com.peterlaurence.book.javatokotlin.threadsafety.samples.listSameTime

import java.util.*


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