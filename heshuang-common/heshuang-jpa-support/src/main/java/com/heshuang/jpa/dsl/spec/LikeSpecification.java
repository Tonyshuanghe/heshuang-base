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

public class LikeSpecification<T> extends AbstractSpecification<T> {
    private final String property;
    private final String[] patterns;

    public LikeSpecification(String property, String... patterns) {
        this.property = property;
        this.patterns = patterns;
    }

    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        From from = this.getRoot(this.property, root);
        String field = this.getProperty(this.property);
        if (this.patterns.length == 1) {
            return cb.like(from.get(field), this.patterns[0]);
        } else {
            Predicate[] predicates = new Predicate[this.patterns.length];

            for(int i = 0; i < this.patterns.length; ++i) {
                predicates[i] = cb.like(from.get(field), this.patterns[i]);
            }

            return cb.or(predicates);
        }
    }
}
