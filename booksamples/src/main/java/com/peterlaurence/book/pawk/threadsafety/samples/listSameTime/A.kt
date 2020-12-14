package com.peterlaurence.book.pawk.threadsafety.samples.listSameTime

import java.util.*

class AKt {
    var aList: MutableList<Int> = ArrayList()

    fun add() {
        val last = aList.last()  // equivalent of aList[aList.size - 1]
        aList.add(last + 1)
    }

    init {
        aList.add(1)
    }
}
