package com.aman.interview;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum Ingredients {
    MILK("milk",1.5),
    SUGAR("sugar",1),
    SODA("soda",1),
    MINT("mint",1),
    WATER("water", 1),
    // This was added as a hack, while testing. Again time constraint.
    DUMMY("dummy",0);

    private final String name;
    private final double price;
    public static final Map<String, Ingredients> ingredientResolver ;

    static {
        ingredientResolver = Collections.unmodifiableMap(
            Arrays.stream(Ingredients.values()).collect(Collectors.toMap(e -> e.getName().toUpperCase(), Function.identity()))
            );
    }

    Ingredients(String name, double price) {
        this.price = price;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
    

}

