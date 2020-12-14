package com.peterlaurence.book.pawk.threadsafety.samples.listSameTime;

import java.util.ArrayList;
import java.util.List;

public class A {
    List<Integer> aList = new ArrayList<>();
    {
        aList.add(1);
    }

    void add() {
        Integer last = aList.get(aList.size() - 1);
        aList.add(last + 1);
    }
}
