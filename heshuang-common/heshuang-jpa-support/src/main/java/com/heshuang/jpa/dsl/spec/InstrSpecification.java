//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.jpa.dsl.spec;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.query.criteria.internal.CriteriaBuilderImpl;
import org.hibernate.query.criteria.internal.expression.LiteralExpression;

public class InstrSpecification<T> extends AbstractSpecification<T> {
    private String property;
    private String value;
    private InstrOpts opts;

    public InstrSpecification(String property, String values) {
        this.opts = InstrOpts.ALL;
        this.property = property;
        this.value = values;
    }

    public InstrSpecification(InstrOpts opts, String property, String values) {
        this.opts = InstrOpts.ALL;
        this.property = property;
        this.value = values;
        this.opts = opts;
    }

    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        LiteralExpression literalExpression = new LiteralExpression((CriteriaBuilderImpl)null, this.value);
        Expression<Integer> instrPosition = cb.function("instr", Integer.class, new Expression[]{root.get(this.property), literalExpression});
        Predicate predicate = cb.gt(instrPosition, 0);
        switch(this.opts) {
        case END:
            predicate = cb.equal(instrPosition, 1);
        default:
            return predicate;
        }
    }

    public static enum InstrOpts {
        END,
        ALL;

        private InstrOpts() {
        }
    }
}
