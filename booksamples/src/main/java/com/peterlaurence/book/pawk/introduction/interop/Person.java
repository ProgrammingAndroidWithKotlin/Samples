package com.peterlaurence.book.pawk.introduction.interop;

public class Person {
    private String mName = null;

    /* Constructor hidden for brevity */
    Person(String name) {
        mName = name;
    }

    String getName() {
        return mName;
    }
}
