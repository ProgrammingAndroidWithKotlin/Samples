package com.peterlaurence.book.pawk.threadsafety.samples.listSameTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AWrong {
    List<Integer> list = Collections.synchronizedList(new ArrayList<Integer>() {{
        add(1);
    }});

    void add() {
        Integer last = list.get(list.size() - 1);
        list.add(last + 1);
    }
}
