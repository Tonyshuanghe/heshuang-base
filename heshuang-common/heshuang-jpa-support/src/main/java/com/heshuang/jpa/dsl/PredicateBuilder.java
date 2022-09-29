//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.jpa.dsl;

import com.google.common.collect.Lists;
import com.heshuang.jpa.dsl.spec.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Predicate.BooleanOperator;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;

public class PredicateBuilder<T> {
    private final BooleanOperator operator;
    private List<Specification<T>> specifications;

    public PredicateBuilder(BooleanOperator operator) {
        this.operator = operator;
        this.specifications = new ArrayList();
    }

    public PredicateBuilder<T> eq(String property, Object... values) {
        return this.eq(true, property, values);
    }

    public PredicateBuilder<T> eq(boolean condition, String property, Object... values) {
        return this.predicate(condition, new EqualSpecification(property, values));
    }

    public PredicateBuilder<T> ne(String property, Object... values) {
        return this.ne(true, property, values);
    }

    public PredicateBuilder<T> ne(boolean condition, String property, Object... values) {
        return this.predicate(condition, new NotEqualSpecification(property, values));
    }

    public PredicateBuilder<T> gt(String property, Comparable<?> compare) {
        return this.gt(true, property, compare);
    }

    public PredicateBuilder<T> gt(boolean condition, String property, Comparable<?> compare) {
        return this.predicate(condition, new GtSpecification(property, compare));
    }

    public PredicateBuilder<T> ge(String property, Comparable<?> compare) {
        return this.ge(true, property, compare);
    }

    public PredicateBuilder<T> ge(boolean condition, String property, Comparable<? extends Object> compare) {
        return this.predicate(condition, new GeSpecification(property, compare));
    }

    public PredicateBuilder<T> lt(String property, Comparable<?> number) {
        return this.lt(true, property, number);
    }

    public PredicateBuilder<T> lt(boolean condition, String property, Comparable<?> compare) {
        return this.predicate(condition, new LtSpecification(property, compare));
    }

    public PredicateBuilder<T> le(String property, Comparable<?> compare) {
        return this.le(true, property, compare);
    }

    public PredicateBuilder<T> le(boolean condition, String property, Comparable<?> compare) {
        return this.predicate(condition, new LeSpecification(property, compare));
    }

    public PredicateBuilder<T> between(String property, Object lower, Object upper) {
        return this.between(true, property, lower, upper);
    }

    public PredicateBuilder<T> between(boolean condition, String property, Object lower, Object upper) {
        return this.predicate(condition, new BetweenSpecification(property, lower, upper));
    }

    public PredicateBuilder<T> like(String property, String... patterns) {
        return this.like(true, property, patterns);
    }

    public PredicateBuilder<T> like(boolean condition, String property, String... patterns) {
        return this.predicate(condition, new LikeSpecification(property, patterns));
    }

    public PredicateBuilder<T> instr(String property, String patterns) {
        return this.like(true, property, patterns);
    }

    public PredicateBuilder<T> instr(boolean condition, String property, String patterns) {
        return this.predicate(condition, new InstrSpecification(property, patterns));
    }

    public PredicateBuilder<T> instrEnd(String property, String patterns) {
        return this.like(true, property, patterns);
    }

    public PredicateBuilder<T> instrEnd(boolean condition, String property, String patterns) {
        return this.predicate(condition, new InstrSpecification(InstrSpecification.InstrOpts.END, property, patterns));
    }

    public PredicateBuilder<T> notLike(String property, String... patterns) {
        return this.notLike(true, property, patterns);
    }

    public PredicateBuilder<T> notLike(boolean condition, String property, String... patterns) {
        return this.predicate(condition, new NotLikeSpecification(property, patterns));
    }

    public PredicateBuilder<T> in(String property, Collection<?> values) {
        return this.in(true, property, values);
    }

    public PredicateBuilder<T> in(boolean condition, String property, Collection<?> values) {
        return this.predicate(condition, new InSpecification(property, values));
    }

    public PredicateBuilder<T> notIn(String property, Collection<?> values) {
        return this.notIn(true, property, values);
    }

    public PredicateBuilder<T> notIn(boolean condition, String property, Collection<?> values) {
        return this.predicate(condition, new NotInSpecification(property, values));
    }

    public PredicateBuilder<T> bitor(String property, List<Number> var) {
        if (!CollectionUtils.isEmpty(var)) {
            this.predicate(true, new BitandSpecification("bitor", property, var));
        }

        return this;
    }

    public PredicateBuilder<T> bitor(String property, Number... var) {
        this.bitor(property, (List)Lists.newArrayList(var));
        return this;
    }

    public PredicateBuilder<T> bitxor(String property, List<Number> var) {
        if (!CollectionUtils.isEmpty(var)) {
            this.predicate(true, new BitandSpecification("bitxor", property, var));
        }

        return this;
    }

    public PredicateBuilder<T> bitxor(String property, Number... var) {
        this.bitor(property, (List)Lists.newArrayList(var));
        return this;
    }

    public PredicateBuilder<T> bitand(String property, Number... var) {
        this.bitand(property, (List)Lists.newArrayList(var));
        return this;
    }

    public PredicateBuilder<T> bitand(String property, List<Number> var) {
        if (!CollectionUtils.isEmpty(var)) {
            this.predicate(true, new BitandSpecification(property, var));
        }

        return this;
    }

    public PredicateBuilder<T> bitin(String property, List<Number> var) {
        if (!CollectionUtils.isEmpty(var)) {
            this.predicate(true, new BitinSpecification(property, var));
        }

        return this;
    }

    public PredicateBuilder<T> bitin(String property, Number... var) {
        this.bitin(property, (List)Lists.newArrayList(var));
        return this;
    }

    public PredicateBuilder<T> predicate(Specification specification) {
        return this.predicate(true, specification);
    }

    public PredicateBuilder<T> predicate(boolean condition, Specification specification) {
        if (condition) {
            this.specifications.add(specification);
        }

        return this;
    }

    public Specification<T> build() {
        return (root, query, cb) -> {
            Predicate[] predicates = new Predicate[this.specifications.size()];

            for(int i = 0; i < this.specifications.size(); ++i) {
                predicates[i] = ((Specification)this.specifications.get(i)).toPredicate(root, query, cb);
            }

            if (Objects.equals(predicates.length, 0)) {
                return null;
            } else {
                return BooleanOperator.OR.equals(this.operator) ? cb.or(predicates) : cb.and(predicates);
            }
        };
    }
}
