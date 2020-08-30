package com.peterlaurence.book.javatokotlin.threadsafety.samples.listSameTime;

import java.util.ArrayList;
import java.util.List;

public class ASynchronized {
    private List<Integer> list = new ArrayList<Integer>() {{
        add(1);
    }};

    synchronized void add() {
        Integer last = list.get(list.size() - 1);
        list.add(last + 1);
    }
}
