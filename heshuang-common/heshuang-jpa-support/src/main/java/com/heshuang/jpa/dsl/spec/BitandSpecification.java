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

public class BitandSpecification<T> extends AbstractSpecification<T> {
    private String property;
    private List<Number> values;
    private String opts = "bitand";

    public BitandSpecification(String property, List<Number> values) {
        this.property = property;
        this.values = values;
    }

    public BitandSpecification(String opts, String property, List<Number> values) {
        this.property = property;
        this.values = values;
        this.opts = opts;
    }

    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        Long sum = this.values.stream().mapToLong((p) -> {
            return p.longValue();
        }).sum();
        LiteralExpression<Long> literalExpression = new LiteralExpression((CriteriaBuilderImpl)null, sum);
        Expression<Long> ruleBit = cb.function(this.opts, Long.class, new Expression[]{root.get(this.property), literalExpression});
        return cb.equal(ruleBit, root.get(this.property));
    }
}
