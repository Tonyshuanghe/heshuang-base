//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.jpa.plugs.model;


import javax.persistence.Query;

import com.heshuang.core.base.exception.BusinessException;
import com.heshuang.jpa.plugs.BoreResultTransformer;
import org.hibernate.query.NativeQuery;

public final class QueryResultBuilder {
    private final Query query;
    private final String resultType;

    public QueryResultBuilder(Query query, String resultType) {
        this.query = query;
        this.resultType = resultType;
    }

    public Query build(boolean isNative) {
        if (isNative) {
            ((NativeQuery)this.query.unwrap(NativeQuery.class)).setResultTransformer(new BoreResultTransformer(this.getResultTypeClass()));
        } else {
            ((org.hibernate.query.Query)this.query.unwrap(org.hibernate.query.Query.class)).setResultTransformer(new BoreResultTransformer(this.getResultTypeClass()));
        }

        return this.query;
    }

    private Class<?> getResultTypeClass() {
        try {
            return Class.forName(this.resultType);
        } catch (ClassNotFoundException var2) {
            throw BusinessException.of("未找到【" + this.resultType + "】对应的 class，请检查！", var2);
        }
    }
}
