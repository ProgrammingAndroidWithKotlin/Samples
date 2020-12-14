package com.peterlaurence.book.pawk.threadsafety.samples.listSameTime


class ASynchronizedKt {
    val list: MutableList<Int> = mutableListOf(1)

    @Synchronized
    fun add() {
        val last = list.last()
        list.add(last + 1)
    }
}