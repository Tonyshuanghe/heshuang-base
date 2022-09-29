//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.jpa.dsl;

import javax.persistence.criteria.Predicate.BooleanOperator;

public final class Specifications {
    private Specifications() {
    }

    public static <T> PredicateBuilder<T> and() {
        return new PredicateBuilder(BooleanOperator.AND);
    }

    public static <T> PredicateBuilder<T> or() {
        return new PredicateBuilder(BooleanOperator.OR);
    }
}
