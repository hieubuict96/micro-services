package com.onlyfullstack.eurekanamingservice;

import java.util.function.Function;

public class A {
    public void a() {
        Function<String, Integer> function = Integer::valueOf;
        function.apply("3");
    }
}
