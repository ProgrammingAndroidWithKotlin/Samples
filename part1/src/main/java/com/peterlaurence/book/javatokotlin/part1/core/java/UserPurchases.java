package com.peterlaurence.book.javatokotlin.part1.core.java;

import java.util.Collections;
import java.util.List;

public class UserPurchases {
    public final String user;
    public final List<String> purchases;

    public UserPurchases(String user, List<String> purchases) {
        this.user = user;
        this.purchases = Collections.unmodifiableList(purchases);
    }
}
