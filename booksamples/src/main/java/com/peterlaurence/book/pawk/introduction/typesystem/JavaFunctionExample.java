package com.peterlaurence.book.pawk.introduction.typesystem;

import java.util.function.Function;

public class JavaFunctionExample {

    public static void main(String[] args) {
        Function<Double, Double> func = x -> Math.pow(2.0, x);
        Double result = func.apply(4.0);
        System.out.println(result);
    }

}