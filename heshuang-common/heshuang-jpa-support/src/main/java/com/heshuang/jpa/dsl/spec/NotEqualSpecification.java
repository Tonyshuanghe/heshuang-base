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

public class NotEqualSpecification<T> extends AbstractSpecification<T> {
    private final String property;
    private final transient Object[] values;

    public NotEqualSpecification(String property, Object... values) {
        this.property = property;
        this.values = values;
    }
    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        From from = this.getRoot(this.property, root);
        String field = this.getProperty(this.property);
        if (this.values == null) {
            return cb.isNotNull(from.get(field));
        } else if (this.values.length == 1) {
            return this.getPredicate(from, cb, this.values[0], field);
        } else {
            Predicate[] predicates = new Predicate[this.values.length];

            for(int i = 0; i < this.values.length; ++i) {
                predicates[i] = this.getPredicate(root, cb, this.values[i], field);
            }

            return cb.or(predicates);
        }
    }

    private Predicate getPredicate(From root, CriteriaBuilder cb, Object value, String field) {
        return value == null ? cb.isNotNull(root.get(field)) : cb.notEqual(root.get(field), value);
    }
}
