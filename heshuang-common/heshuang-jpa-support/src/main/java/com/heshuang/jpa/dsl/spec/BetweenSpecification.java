//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.jpa.dsl.spec;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class BetweenSpecification<T> extends AbstractSpecification<T> {
    private final String property;
    private final transient Comparable<Object> lower;
    private final transient Comparable<Object> upper;

    public BetweenSpecification(String property, Object lower, Object upper) {
        this.property = property;
        this.lower = (Comparable)lower;
        this.upper = (Comparable)upper;
    }

    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        From from = this.getRoot(this.property, root);
        String field = this.getProperty(this.property);
        return cb.between(from.get(field), this.lower, this.upper);
    }
}
