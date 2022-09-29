//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.jpa.plugs;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.Tuple;

import com.heshuang.jpa.plugs.anno.BoreQuery;
import com.heshuang.jpa.plugs.model.QueryResultBuilder;
import com.heshuang.jpa.plugs.model.SqlInfo;
import com.heshuang.jpa.plugs.utils.ClassMethodInvoker;
import com.heshuang.jpa.plugs.utils.QueryHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.query.AbstractJpaQuery;
import org.springframework.data.jpa.repository.query.JpaParameters;
import org.springframework.data.jpa.repository.query.JpaParametersParameterAccessor;
import org.springframework.data.jpa.repository.query.JpaQueryMethod;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.data.repository.query.Parameter;
import org.springframework.data.repository.query.ParametersParameterAccessor;
import org.springframework.data.repository.query.ReturnedType;

public class BoreJpaQuery extends AbstractJpaQuery {
    private static final String REGX_SELECT_FROM = "((?i)select)([\\s\\S]*?)((?i)from)";
    private static final String SELECT_COUNT = "select count(*) as count from ";
    private static final String REGX_SELECT_FROM_DISTINCT = "((?i)select)([\\s\\S]*?)((?i)distinct)\\s+([\\s\\S]*?)((?i)from)";
    private static final String REGX_SQL_ALIAS = "\\s+((?i)as)\\s+[^,\\s]+";
    private static final Pattern SELECT_FROM_PATTERN = Pattern.compile("((?i)select)([\\s\\S]*?)((?i)from)");
    private static final Pattern SELECT_FROM_DISTINCT_PATTERN = Pattern.compile("((?i)select)([\\s\\S]*?)((?i)distinct)\\s+([\\s\\S]*?)((?i)from)");
    private JpaParameters jpaParams;
    private BoreQuery boreQuery;
    private Class<?> queryClass;

    BoreJpaQuery(JpaQueryMethod method, EntityManager em) {
        super(method, em);
    }

    protected Query doCreateQuery(JpaParametersParameterAccessor accessor) {
        return this.doCreateQuery(accessor.getValues());
    }

    protected Query doCreateQuery(Object[] values) {
        JpaQueryMethod jpaMethod = super.getQueryMethod();
        this.jpaParams = jpaMethod.getParameters();
        BoreQueryInfo boreQueryInfo = BoreQueryInfo.getInstance();
        boreQueryInfo.setContextParams(this.buildContextParams(values));
        SqlInfo sqlInfo = this.getSqlInfoByBore();
        boreQueryInfo.setSqlInfo(sqlInfo);
        boreQueryInfo.setQuerySql(sqlInfo.getSql());
        Pageable pageable = this.buildPagableAndSortSql(values, boreQueryInfo.getQuerySql());
        EntityManager em = super.getEntityManager();
        String querySql = boreQueryInfo.getQuerySql();
        Query query;
        if (this.boreQuery.nativeQuery()) {
            ReturnedType returnedType = jpaMethod.getResultProcessor().withDynamicProjection(new ParametersParameterAccessor(jpaMethod.getParameters(), values)).getReturnedType();
            Class<?> returnClassType = returnedType.getReturnedType();
            Class<?> type = this.getTypeToQueryFor(returnedType, querySql);
            if (!jpaMethod.isQueryForEntity() && !isPrimitive(returnClassType) && !returnClassType.isAssignableFrom(Map.class) && !returnClassType.isAssignableFrom(Tuple.class) && !jpaMethod.isPageQuery()) {
                sqlInfo.setResultTypeClass(returnedType.getReturnedType());
            }

            query = type == null ? em.createNativeQuery(querySql) : em.createNativeQuery(querySql, type);
        } else {
            query = em.createQuery(querySql);
        }

        Iterator var12 = sqlInfo.getParams().keySet().iterator();

        while(true) {
            String key;
            Object value;
            do {
                if (!var12.hasNext()) {
                    if (pageable != null && pageable.isPaged()) {
                        query.setFirstResult((int)pageable.getOffset());
                        query.setMaxResults(pageable.getPageSize());
                    }

                    String resultType = sqlInfo.getResultType();
                    if (StringUtils.isNotBlank(resultType)) {
                        query = (new QueryResultBuilder(query, resultType)).build(this.boreQuery.nativeQuery());
                    }

                    if (pageable == null || pageable.isUnpaged()) {
                        boreQueryInfo.remove();
                    }

                    return query;
                }

                key = (String)var12.next();
                value = sqlInfo.getParams().get(key);
            } while(querySql.indexOf(":" + key) <= 0 && querySql.indexOf("#" + key) <= 0);

            query.setParameter(key, value);
        }
    }

    public static boolean isPrimitive(Class clz) {
        return clz.isPrimitive() || clz == Byte.class || clz == Character.class || clz == Boolean.class || clz == Short.class || clz == Integer.class || clz == Long.class || clz == String.class || clz == Float.class || clz == Double.class || clz == BigDecimal.class;
    }

    @Override
    protected Query doCreateCountQuery(JpaParametersParameterAccessor accessor) {
        return this.doCreateCountQuery(accessor.getValues());
    }

    protected Query doCreateCountQuery(Object[] values) {
        String countSql = this.getCountSql();
        EntityManager em = this.getEntityManager();
        Query query = this.boreQuery.nativeQuery() ? em.createNativeQuery(countSql) : em.createQuery(countSql, Long.class);
        Iterator var5 = BoreQueryInfo.getInstance().getSqlInfo().getParams().keySet().iterator();

        while(true) {
            String key;
            Object value;
            do {
                if (!var5.hasNext()) {
                    BoreQueryInfo boreQueryInfo = BoreQueryInfo.getLocalThreadInstance();
                    if (boreQueryInfo != null) {
                        boreQueryInfo.remove();
                    }

                    return (Query)query;
                }

                key = (String)var5.next();
                value = BoreQueryInfo.getInstance().getSqlInfo().getParams().get(key);
            } while(countSql.indexOf(":" + key) <= 0 && countSql.indexOf("#" + key) <= 0);

            ((Query)query).setParameter(key, value);
        }
    }

    private Class<?> getTypeToQueryFor(ReturnedType returnedType, String querySql) {
        Class<?> result = this.getQueryMethod().isQueryForEntity() ? returnedType.getDomainType() : null;
        if (!QueryUtils.hasConstructorExpression(querySql) && !QueryUtils.getProjection(querySql).equalsIgnoreCase(QueryHelper.detectAlias(querySql))) {
            return returnedType.isProjecting() && !this.getMetamodel().isJpaManaged(returnedType.getReturnedType()) ? Tuple.class : result;
        } else {
            return result;
        }
    }

    private Map<String, Object> buildContextParams(Object[] values) {
        int len = this.jpaParams.getNumberOfParameters();
        Map<String, Object> context = new HashMap(len);

        for(int i = 0; i < len; ++i) {
            Parameter parameter = this.jpaParams.getParameter(i);
            if (!parameter.isSpecialParameter()) {
                Optional<String> nameOptional = parameter.getName();
                if (nameOptional.isPresent()) {
                    context.put((String)nameOptional.get(), values[i]);
                }
            }
        }

        return context;
    }

    private SqlInfo getSqlInfoByBore() {
        Class<?> provider = this.boreQuery.provider();
        String method = this.boreQuery.method();
        String sqlStr = this.boreQuery.value();
        Map<String, Object> contextParams = BoreQueryInfo.getInstance().getContextParams();
        if (StringUtils.isBlank(method)) {
            method = this.getQueryMethod().getName();
        }

        if (provider != Void.class) {
            if (StringUtils.isNotBlank(method)) {
                return ClassMethodInvoker.invoke(provider, method, contextParams);
            } else {
                return StringUtils.isNotBlank(sqlStr) ? this.getStrSqlInfo(sqlStr, contextParams) : ClassMethodInvoker.invoke(provider, this.getQueryMethod().getName(), contextParams);
            }
        } else {
            return this.getStrSqlInfo(sqlStr, contextParams);
        }
    }

    private SqlInfo getStrSqlInfo(String sqlStr, Map<String, Object> contextParams) {
        return BoreSql.getStrSqlInfo(sqlStr, contextParams);
    }

    private Pageable buildPagableAndSortSql(Object[] values, String querySql) {
        Pageable pageable = null;
        BoreQueryInfo boreQueryInfo = BoreQueryInfo.getInstance();
        if (this.jpaParams.hasPageableParameter()) {
            pageable = (Pageable)values[this.jpaParams.getPageableIndex()];
            if (pageable != null) {
                boreQueryInfo.setQuerySql(QueryUtils.applySorting(querySql, pageable.getSort(), QueryHelper.detectAlias(querySql)));
            }
        }

        if (this.jpaParams.hasSortParameter()) {
            boreQueryInfo.setQuerySql(QueryUtils.applySorting(querySql, (new ParametersParameterAccessor(this.jpaParams, values)).getSort(), QueryHelper.detectAlias(querySql)));
        }

        return pageable;
    }

    private String getCountSql() {
        Class<?> provider = this.boreQuery.provider();
        String strCountQuery = this.boreQuery.countQuery();
        String countMethod = this.boreQuery.countMethod();
        BoreQueryInfo boreQueryInfo = BoreQueryInfo.getInstance();
        Map<String, Object> contextParams = boreQueryInfo.getContextParams();
        if (provider != Void.class) {
            if (StringUtils.isNotBlank(countMethod)) {
                boreQueryInfo.setSqlInfo(ClassMethodInvoker.invoke(provider, countMethod, contextParams));
                return boreQueryInfo.getSqlInfo().getSql();
            } else if (StringUtils.isNotBlank(strCountQuery)) {
                boreQueryInfo.setSqlInfo(this.getStrSqlInfo(strCountQuery, contextParams));
                return boreQueryInfo.getSqlInfo().getSql();
            } else {
                return this.getCountSqlByQueryInfo(boreQueryInfo);
            }
        } else if (StringUtils.isNotBlank(strCountQuery)) {
            boreQueryInfo.setSqlInfo(this.getStrSqlInfo(strCountQuery, contextParams));
            return boreQueryInfo.getSqlInfo().getSql();
        } else {
            return this.getCountSqlByQueryInfo(boreQueryInfo);
        }
    }

    private String getCountSqlByQueryInfo(BoreQueryInfo boreQueryInfo) {
        boolean enableDistinct = this.boreQuery.enableDistinct();
        String infoSql = boreQueryInfo.getSqlInfo().getSql();
        Matcher matcher = SELECT_FROM_PATTERN.matcher(infoSql);
        String countSql = matcher.replaceFirst("select count(*) as count from ");
        if (StringUtils.isNotBlank(countSql) && countSql.toLowerCase().startsWith("from")) {
            countSql = String.format("select count(*) as count %s", countSql);
        }

        if (!enableDistinct) {
            return countSql;
        } else {
            String selectPrefix = matcher.group();
            matcher = SELECT_FROM_DISTINCT_PATTERN.matcher(selectPrefix);
            if (!matcher.find()) {
                return countSql;
            } else {
                String distinctColumn = matcher.group(4).replaceAll("\\s+((?i)as)\\s+[^,\\s]+", "");
                return countSql.replaceFirst("count\\(\\*\\)", String.format("count(distinct %s)", distinctColumn));
            }
        }
    }

    public void setBoreQuery(BoreQuery boreQuery) {
        this.boreQuery = boreQuery;
    }

    public void setQueryClass(Class<?> queryClass) {
        this.queryClass = queryClass;
    }
}
