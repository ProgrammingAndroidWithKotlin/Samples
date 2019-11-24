package com.peterlaurence.book.javatokotlin.part1.samples.listSameTime;

import java.util.Arrays;
import java.util.List;

public class A {
    List<Integer> aList = Arrays.asList(1);

    void add() {
        Integer last = aList.get(aList.size() - 1);
        aList.add(last + 1);
    }
}
