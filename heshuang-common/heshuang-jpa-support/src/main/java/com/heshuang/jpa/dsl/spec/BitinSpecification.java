//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.jpa.dsl.spec;

import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.query.criteria.internal.CriteriaBuilderImpl;
import org.hibernate.query.criteria.internal.expression.LiteralExpression;

public class BitinSpecification<T> extends AbstractSpecification<T> {
    private String property;
    private List<Number> values;

    public BitinSpecification(String property, List<Number> values) {
        this.property = property;
        this.values = values;
    }

    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        Long sum = this.values.stream().mapToLong((p) -> {
            return p.longValue();
        }).sum();
        LiteralExpression<Long> literalExpression = new LiteralExpression((CriteriaBuilderImpl)null, sum);
        Expression<Long> ruleBit = cb.function("bitand", Long.class, new Expression[]{literalExpression, root.get(this.property)});
        return cb.equal(ruleBit, root.get(this.property));
    }
}
