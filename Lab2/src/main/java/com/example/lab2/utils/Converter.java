package com.example.lab2.utils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class Converter {
    public static <T> List<T> iterableToArrayList(Iterable<T> iterable) {
        // Workaround: convert Iterable to ArrayList without using Guava
        return StreamSupport.stream(iterable.spliterator(), false).collect(Collectors.toList());
    }
}

