//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.jpa.plugs;


import java.util.Collection;
import java.util.Map;
import java.util.function.Consumer;

import com.google.common.collect.Maps;
import com.heshuang.core.base.exception.BusinessException;
import com.heshuang.jpa.plugs.model.BoreAction;
import com.heshuang.jpa.plugs.model.BuildSource;
import com.heshuang.jpa.plugs.model.JavaSqlInfoBuilder;
import com.heshuang.jpa.plugs.model.SqlInfo;
import com.heshuang.jpa.plugs.utils.StringHelper;
import org.apache.commons.lang3.StringUtils;

public final class BoreSql {
    private static final String START = "_start";
    private static final String END = "_end";
    private static final Map<String, Object> startMap = Maps.newHashMap();
    private static final Map<String, Object> endMap = Maps.newHashMap();
    private BuildSource source = new BuildSource(new SqlInfo());

    private BoreSql() {
    }

    public static BoreSql builder() {
        return new BoreSql();
    }

    public SqlInfo build() {
        SqlInfo sqlInfo = this.source.getSqlInfo();
        sqlInfo.setSql(StringHelper.replaceWhereAndOr(StringHelper.replaceBlank(sqlInfo.getJoin().toString())));
        return sqlInfo;
    }

    public static SqlInfo getStrSqlInfo(String sqlStr, Map context) {
        if (StringUtils.isBlank(sqlStr)) {
            throw BusinessException.of("查询sql字符串不能为空");
        } else {
            SqlInfo sqlInfo = new SqlInfo();
            sqlInfo.setSql(StringHelper.eval(sqlStr, context));
            sqlInfo.setParams(context);
            return sqlInfo;
        }
    }

    public static void main(String[] args) {
    }

    private BoreSql concat(String sqlKey, String... params) {
        this.source.getSqlInfo().getJoin().append(" ").append(sqlKey).append(" ");
        if (params != null && params.length > 0) {
            String[] var3 = params;
            int var4 = params.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                String param = var3[var5];
                this.source.getSqlInfo().getJoin().append(param).append(" ");
            }
        }

        return this;
    }

    public BoreSql insertInto(String text) {
        return this.concat("INSERT INTO", text);
    }

    public BoreSql values(String text) {
        return this.concat("VALUES", text);
    }

    public BoreSql deleteFrom(String text) {
        return this.concat("DELETE FROM", text);
    }

    public BoreSql update(String text) {
        return this.concat("UPDATE", text);
    }

    public BoreSql select(String text) {
        return this.concat("SELECT", text);
    }

    public BoreSql from(String text) {
        return this.concat("FROM", text);
    }

    public BoreSql where() {
        this.concat("WHERE");
        return this;
    }

    public BoreSql where(String text) {
        this.concat("WHERE", text);
        return this;
    }

    public BoreSql where(String text, Map<String, Object> paramMap) {
        this.concat("WHERE", text);
        return this.params(paramMap);
    }

    public BoreSql where(String text, String key, Object value) {
        this.concat("WHERE", text);
        return this.param(key, value);
    }

    public BoreSql where(Consumer<BoreSql> consumer) {
        this.source.getSqlInfo().setPrependWhere(true);
        consumer.accept(this);
        return this;
    }

    public BoreSql whereDynamic() {
        this.source.getSqlInfo().setPrependWhere(true);
        return this;
    }

    public BoreSql and() {
        return this.concat("AND");
    }

    public BoreSql and(String text) {
        return this.concat("AND", text);
    }

    public BoreSql or() {
        return this.concat("OR");
    }

    public BoreSql or(String text) {
        return this.concat("OR", text);
    }

    public BoreSql as(String text) {
        return this.concat("AS", text);
    }

    public BoreSql set(String text) {
        return this.concat("SET", text);
    }

    public BoreSql innerJoin(String text) {
        return this.concat("INNER JOIN", text);
    }

    public BoreSql leftJoin(String text) {
        return this.concat("LEFT JOIN", text);
    }

    public BoreSql rightJoin(String text) {
        return this.concat("RIGHT JOIN", text);
    }

    public BoreSql fullJoin(String text) {
        return this.concat("FULL JOIN", text);
    }

    public BoreSql on(String text) {
        return this.concat("ON", text);
    }

    public BoreSql orderBy(String text) {
        return this.concat("ORDER BY", text);
    }

    public BoreSql groupBy(String text) {
        return this.concat("GROUP BY", text);
    }

    public BoreSql having(String text) {
        return this.concat("HAVING", text);
    }

    public BoreSql limit(String text) {
        return this.concat("LIMIT", text);
    }

    public BoreSql offset(String text) {
        return this.concat("OFFSET", text);
    }

    public BoreSql asc() {
        return this.concat("ASC");
    }

    public BoreSql desc() {
        return this.concat("DESC");
    }

    public BoreSql union() {
        return this.concat("UNION");
    }

    public BoreSql unionAll() {
        return this.concat("UNION ALL");
    }

    public BoreSql text(String text) {
        this.source.getSqlInfo().getJoin().append(text);
        return this;
    }

    public BoreSql text(String text, String key, Object value) {
        this.source.getSqlInfo().getJoin().append(text);
        this.param(key, value);
        return this;
    }

    public BoreSql text(String text, Map<String, Object> paramMap) {
        this.source.getSqlInfo().getJoin().append(text);
        this.params(paramMap);
        return this;
    }

    public BoreSql text(boolean match, String text, String key, Object value) {
        return match ? this.text(text, key, value) : this;
    }

    public BoreSql text(boolean match, String text, Map<String, Object> paramMap) {
        return match ? this.text(text, paramMap) : this;
    }

    public BoreSql param(String key, Object value) {
        if (StringHelper.isBlank(key)) {
            throw BusinessException.of("【BoreSql 异常提示】添加的命名参数名称为空！");
        } else {
            this.source.getSqlInfo().getParams().put(key, value);
            return this;
        }
    }

    public BoreSql params(Map<String, Object> paramMap) {
        if (paramMap == null) {
            throw BusinessException.of("【BoreSql 异常提示】添加的命名参数 Map 为null");
        } else {
            paramMap.forEach(this::param);
            return this;
        }
    }

    public BoreSql doAny(BoreAction action) {
        SqlInfo sqlInfo = this.source.getSqlInfo();
        action.execute(sqlInfo.getJoin(), sqlInfo.getParams());
        return this;
    }

    public BoreSql doAny(boolean match, BoreAction action) {
        return match ? this.doAny(action) : this;
    }

    private BoreSql doNormal(String prefix, String field, String name, Object value, String symbol, boolean match) {
        if (match) {
            this.source.setPrefix(prefix).setSymbol(symbol);
            (new JavaSqlInfoBuilder(this.source)).buildNormalSql(field, StringHelper.isBlank(name) ? StringHelper.fixDot(field) : name, value);
            this.source.reset();
        }

        return this;
    }

    private BoreSql doLike(String prefix, String field, String name, Object value, boolean match, boolean positive, Map<String, Object> likeTypeMap) {
        if (match) {
            this.source.setPrefix(prefix).setSymbol(positive ? " LIKE " : " NOT LIKE ").setOthers(likeTypeMap);
            (new JavaSqlInfoBuilder(this.source)).buildLikeSql(field, StringHelper.isBlank(name) ? StringHelper.fixDot(field) : name, value);
            this.source.reset();
        }

        return this;
    }

    private BoreSql doLikePattern(String prefix, String field, String pattern, boolean match, boolean positive) {
        if (match) {
            this.source.setPrefix(prefix).setSymbol(positive ? " LIKE " : " NOT LIKE ");
            (new JavaSqlInfoBuilder(this.source)).buildLikePatternSql(field, pattern);
            this.source.reset();
        }

        return this;
    }

    private BoreSql doBetween(String prefix, String field, String startName, Object startValue, String endName, Object endValue, boolean match) {
        if (match) {
            this.source.setPrefix(prefix);
            (new JavaSqlInfoBuilder(this.source)).buildBetweenSql(field, StringHelper.isBlank(startName) ? field + "_start" : startName, startValue, StringHelper.isBlank(endName) ? field + "_end" : endName, endValue);
            this.source.reset();
        }

        return this;
    }

    private BoreSql doInByType(String prefix, String field, String name, Object value, boolean match, boolean positive) {
        if (match) {
            this.source.setPrefix(prefix).setSymbol(positive ? " IN " : " NOT IN ");
            (new JavaSqlInfoBuilder(this.source)).buildInSql(field, StringHelper.isBlank(name) ? StringHelper.fixDot(field) : name, value);
            this.source.reset();
        }

        return this;
    }

    private BoreSql doIn(String prefix, String field, String name, Object[] values, boolean match, boolean positive) {
        return this.doInByType(prefix, field, name, values, match, positive);
    }

    private BoreSql doIn(String prefix, String field, String name, Collection<?> values, boolean match, boolean positive) {
        return this.doInByType(prefix, field, name, values, match, positive);
    }

    private BoreSql doIsNull(String prefix, String field, boolean match, boolean positive) {
        if (match) {
            this.source = this.source.setPrefix(prefix).setSymbol(positive ? " IS NULL " : " IS NOT NULL ");
            (new JavaSqlInfoBuilder(this.source)).buildIsNullSql(field);
            this.source.reset();
        }

        return this;
    }

    public BoreSql equal(String field, Object value) {
        return this.doNormal(" ", field, (String)null, value, " = ", true);
    }

    public BoreSql equal(String field, Object value, String name) {
        return this.doNormal(" ", field, name, value, " = ", true);
    }

    public BoreSql equal(String field, Object value, boolean match) {
        return this.doNormal(" ", field, (String)null, value, " = ", match);
    }

    public BoreSql equal(String field, Object value, String name, boolean match) {
        return this.doNormal(" ", field, name, value, " = ", match);
    }

    public BoreSql andEqual(String field, Object value) {
        return this.doNormal(" AND ", field, (String)null, value, " = ", true);
    }

    public BoreSql andEqual(String field, Object value, String name) {
        return this.doNormal(" AND ", field, name, value, " = ", true);
    }

    public BoreSql andEqual(String field, Object value, boolean match) {
        return this.doNormal(" AND ", field, (String)null, value, " = ", match);
    }

    public BoreSql andEqual(String field, Object value, String name, boolean match) {
        return this.doNormal(" AND ", field, name, value, " = ", match);
    }

    public BoreSql orEqual(String field, Object value) {
        return this.doNormal(" OR ", field, (String)null, value, " = ", true);
    }

    public BoreSql orEqual(String field, Object value, String name) {
        return this.doNormal(" OR ", field, name, value, " = ", true);
    }

    public BoreSql orEqual(String field, Object value, boolean match) {
        return this.doNormal(" OR ", field, (String)null, value, " = ", match);
    }

    public BoreSql orEqual(String field, Object value, String name, boolean match) {
        return this.doNormal(" OR ", field, name, value, " = ", match);
    }

    public BoreSql notEqual(String field, Object value) {
        return this.doNormal(" ", field, (String)null, value, " <> ", true);
    }

    public BoreSql notEqual(String field, Object value, String name) {
        return this.doNormal(" ", field, name, value, " <> ", true);
    }

    public BoreSql notEqual(String field, Object value, boolean match) {
        return this.doNormal(" ", field, (String)null, value, " <> ", match);
    }

    public BoreSql notEqual(String field, Object value, String name, boolean match) {
        return this.doNormal(" ", field, name, value, " <> ", match);
    }

    public BoreSql andNotEqual(String field, Object value) {
        return this.doNormal(" AND ", field, (String)null, value, " <> ", true);
    }

    public BoreSql andNotEqual(String field, Object value, String name) {
        return this.doNormal(" AND ", field, name, value, " <> ", true);
    }

    public BoreSql andNotEqual(String field, Object value, boolean match) {
        return this.doNormal(" AND ", field, (String)null, value, " <> ", match);
    }

    public BoreSql andNotEqual(String field, Object value, String name, boolean match) {
        return this.doNormal(" AND ", field, name, value, " <> ", match);
    }

    public BoreSql orNotEqual(String field, Object value) {
        return this.doNormal(" OR ", field, (String)null, value, " <> ", true);
    }

    public BoreSql orNotEqual(String field, Object value, String name) {
        return this.doNormal(" OR ", field, name, value, " <> ", true);
    }

    public BoreSql orNotEqual(String field, Object value, boolean match) {
        return this.doNormal(" OR ", field, (String)null, value, " <> ", match);
    }

    public BoreSql orNotEqual(String field, Object value, String name, boolean match) {
        return this.doNormal(" OR ", field, name, value, " <> ", match);
    }

    public BoreSql greaterThan(String field, Object value) {
        return this.doNormal(" ", field, (String)null, value, " > ", true);
    }

    public BoreSql greaterThan(String field, Object value, String name) {
        return this.doNormal(" ", field, name, value, " > ", true);
    }

    public BoreSql greaterThan(String field, Object value, boolean match) {
        return this.doNormal(" ", field, (String)null, value, " > ", match);
    }

    public BoreSql greaterThan(String field, Object value, String name, boolean match) {
        return this.doNormal(" ", field, name, value, " > ", match);
    }

    public BoreSql andGreaterThan(String field, Object value) {
        return this.doNormal(" AND ", field, (String)null, value, " > ", true);
    }

    public BoreSql andGreaterThan(String field, Object value, String name) {
        return this.doNormal(" AND ", field, name, value, " > ", true);
    }

    public BoreSql andGreaterThan(String field, Object value, boolean match) {
        return this.doNormal(" AND ", field, (String)null, value, " > ", match);
    }

    public BoreSql andGreaterThan(String field, Object value, String name, boolean match) {
        return this.doNormal(" AND ", field, name, value, " > ", match);
    }

    public BoreSql orGreaterThan(String field, Object value) {
        return this.doNormal(" OR ", field, (String)null, value, " > ", true);
    }

    public BoreSql orGreaterThan(String field, Object value, String name) {
        return this.doNormal(" OR ", field, name, value, " > ", true);
    }

    public BoreSql orGreaterThan(String field, Object value, boolean match) {
        return this.doNormal(" OR ", field, (String)null, value, " > ", match);
    }

    public BoreSql orGreaterThan(String field, Object value, String name, boolean match) {
        return this.doNormal(" OR ", field, name, value, " > ", match);
    }

    public BoreSql lessThan(String field, Object value) {
        return this.doNormal(" ", field, (String)null, value, " < ", true);
    }

    public BoreSql lessThan(String field, Object value, String name) {
        return this.doNormal(" ", field, name, value, " < ", true);
    }

    public BoreSql lessThan(String field, Object value, boolean match) {
        return this.doNormal(" ", field, (String)null, value, " < ", match);
    }

    public BoreSql lessThan(String field, Object value, String name, boolean match) {
        return this.doNormal(" ", field, name, value, " < ", match);
    }

    public BoreSql andLessThan(String field, Object value) {
        return this.doNormal(" AND ", field, (String)null, value, " < ", true);
    }

    public BoreSql andLessThan(String field, Object value, String name) {
        return this.doNormal(" AND ", field, name, value, " < ", true);
    }

    public BoreSql andLessThan(String field, Object value, boolean match) {
        return this.doNormal(" AND ", field, (String)null, value, " < ", match);
    }

    public BoreSql andLessThan(String field, Object value, String name, boolean match) {
        return this.doNormal(" AND ", field, name, value, " < ", match);
    }

    public BoreSql orLessThan(String field, Object value) {
        return this.doNormal(" OR ", field, (String)null, value, " < ", true);
    }

    public BoreSql orLessThan(String field, Object value, String name) {
        return this.doNormal(" OR ", field, name, value, " < ", true);
    }

    public BoreSql orLessThan(String field, Object value, boolean match) {
        return this.doNormal(" OR ", field, (String)null, value, " < ", match);
    }

    public BoreSql orLessThan(String field, Object value, String name, boolean match) {
        return this.doNormal(" OR ", field, name, value, " < ", match);
    }

    public BoreSql greaterThanEqual(String field, Object value) {
        return this.doNormal(" ", field, (String)null, value, " >= ", true);
    }

    public BoreSql greaterThanEqual(String field, Object value, String name) {
        return this.doNormal(" ", field, name, value, " >= ", true);
    }

    public BoreSql greaterThanEqual(String field, Object value, boolean match) {
        return this.doNormal(" ", field, (String)null, value, " >= ", match);
    }

    public BoreSql greaterThanEqual(String field, Object value, String name, boolean match) {
        return this.doNormal(" ", field, name, value, " >= ", match);
    }

    public BoreSql andGreaterThanEqual(String field, Object value) {
        return this.doNormal(" AND ", field, (String)null, value, " >= ", true);
    }

    public BoreSql andGreaterThanEqual(String field, Object value, String name) {
        return this.doNormal(" AND ", field, name, value, " >= ", true);
    }

    public BoreSql andGreaterThanEqual(String field, Object value, boolean match) {
        return this.doNormal(" AND ", field, (String)null, value, " >= ", match);
    }

    public BoreSql andGreaterThanEqual(String field, Object value, String name, boolean match) {
        return this.doNormal(" AND ", field, name, value, " >= ", match);
    }

    public BoreSql orGreaterThanEqual(String field, Object value) {
        return this.doNormal(" OR ", field, (String)null, value, " >= ", true);
    }

    public BoreSql orGreaterThanEqual(String field, Object value, String name) {
        return this.doNormal(" OR ", field, name, value, " >= ", true);
    }

    public BoreSql orGreaterThanEqual(String field, Object value, boolean match) {
        return this.doNormal(" OR ", field, (String)null, value, " >= ", match);
    }

    public BoreSql orGreaterThanEqual(String field, Object value, String name, boolean match) {
        return this.doNormal(" OR ", field, name, value, " >= ", match);
    }

    public BoreSql lessThanEqual(String field, Object value) {
        return this.doNormal(" ", field, (String)null, value, " <= ", true);
    }

    public BoreSql lessThanEqual(String field, Object value, String name) {
        return this.doNormal(" ", field, name, value, " <= ", true);
    }

    public BoreSql lessThanEqual(String field, Object value, boolean match) {
        return this.doNormal(" ", field, (String)null, value, " <= ", match);
    }

    public BoreSql lessThanEqual(String field, Object value, String name, boolean match) {
        return this.doNormal(" ", field, name, value, " <= ", match);
    }

    public BoreSql andLessThanEqual(String field, Object value) {
        return this.doNormal(" AND ", field, (String)null, value, " <= ", true);
    }

    public BoreSql andLessThanEqual(String field, Object value, String name) {
        return this.doNormal(" AND ", field, name, value, " <= ", true);
    }

    public BoreSql andLessThanEqual(String field, Object value, boolean match) {
        return this.doNormal(" AND ", field, (String)null, value, " <= ", match);
    }

    public BoreSql andLessThanEqual(String field, Object value, String name, boolean match) {
        return this.doNormal(" AND ", field, name, value, " <= ", match);
    }

    public BoreSql orLessThanEqual(String field, Object value) {
        return this.doNormal(" OR ", field, (String)null, value, " <= ", true);
    }

    public BoreSql orLessThanEqual(String field, Object value, String name) {
        return this.doNormal(" OR ", field, name, value, " <= ", true);
    }

    public BoreSql orLessThanEqual(String field, Object value, boolean match) {
        return this.doNormal(" OR ", field, (String)null, value, " <= ", match);
    }

    public BoreSql orLessThanEqual(String field, Object value, String name, boolean match) {
        return this.doNormal(" OR ", field, name, value, " <= ", match);
    }

    public BoreSql like(String field, Object value) {
        return this.doLike(" ", field, (String)null, value, true, true, (Map)null);
    }

    public BoreSql like(String field, Object value, String name) {
        return this.doLike(" ", field, name, value, true, true, (Map)null);
    }

    public BoreSql like(String field, Object value, boolean match) {
        return this.doLike(" ", field, (String)null, value, match, true, (Map)null);
    }

    public BoreSql like(String field, Object value, String name, boolean match) {
        return this.doLike(" ", field, name, value, match, true, (Map)null);
    }

    public BoreSql andLike(String field, Object value) {
        return this.doLike(" AND ", field, (String)null, value, true, true, (Map)null);
    }

    public BoreSql andLike(String field, Object value, String name) {
        return this.doLike(" AND ", field, name, value, true, true, (Map)null);
    }

    public BoreSql andLike(String field, Object value, boolean match) {
        return this.doLike(" AND ", field, (String)null, value, match, true, (Map)null);
    }

    public BoreSql andLike(String field, Object value, String name, boolean match) {
        return this.doLike(" AND ", field, name, value, match, true, (Map)null);
    }

    public BoreSql orLike(String field, Object value) {
        return this.doLike(" OR ", field, (String)null, value, true, true, (Map)null);
    }

    public BoreSql orLike(String field, Object value, String name) {
        return this.doLike(" OR ", field, name, value, true, true, (Map)null);
    }

    public BoreSql orLike(String field, Object value, boolean match) {
        return this.doLike(" OR ", field, (String)null, value, match, true, (Map)null);
    }

    public BoreSql orLike(String field, Object value, String name, boolean match) {
        return this.doLike(" OR ", field, name, value, match, true, (Map)null);
    }

    public BoreSql notLike(String field, Object value) {
        return this.doLike(" ", field, (String)null, value, true, false, (Map)null);
    }

    public BoreSql notLike(String field, Object value, String name) {
        return this.doLike(" ", field, name, value, true, false, (Map)null);
    }

    public BoreSql notLike(String field, Object value, boolean match) {
        return this.doLike(" ", field, (String)null, value, match, false, (Map)null);
    }

    public BoreSql notLike(String field, Object value, String name, boolean match) {
        return this.doLike(" ", field, name, value, match, false, (Map)null);
    }

    public BoreSql andNotLike(String field, Object value) {
        return this.doLike(" AND ", field, (String)null, value, true, false, (Map)null);
    }

    public BoreSql andNotLike(String field, Object value, String name) {
        return this.doLike(" AND ", field, name, value, true, false, (Map)null);
    }

    public BoreSql andNotLike(String field, Object value, boolean match) {
        return this.doLike(" AND ", field, (String)null, value, match, false, (Map)null);
    }

    public BoreSql andNotLike(String field, Object value, String name, boolean match) {
        return this.doLike(" AND ", field, name, value, match, false, (Map)null);
    }

    public BoreSql orNotLike(String field, Object value) {
        return this.doLike(" OR ", field, (String)null, value, true, false, (Map)null);
    }

    public BoreSql orNotLike(String field, Object value, String name) {
        return this.doLike(" OR ", field, name, value, true, false, (Map)null);
    }

    public BoreSql orNotLike(String field, Object value, boolean match) {
        return this.doLike(" OR ", field, (String)null, value, match, false, (Map)null);
    }

    public BoreSql orNotLike(String field, Object value, String name, boolean match) {
        return this.doLike(" OR ", field, name, value, match, false, (Map)null);
    }

    public BoreSql startsWith(String field, Object value) {
        return this.doLike(" ", field, (String)null, value, true, true, startMap);
    }

    public BoreSql startsWith(String field, Object value, String name) {
        return this.doLike(" ", field, name, value, true, true, startMap);
    }

    public BoreSql startsWith(String field, Object value, boolean match) {
        return this.doLike(" ", field, (String)null, value, match, true, startMap);
    }

    public BoreSql startsWith(String field, Object value, String name, boolean match) {
        return this.doLike(" ", field, name, value, match, true, startMap);
    }

    public BoreSql andStartsWith(String field, Object value) {
        return this.doLike(" AND ", field, (String)null, value, true, true, startMap);
    }

    public BoreSql andStartsWith(String field, Object value, String name) {
        return this.doLike(" AND ", field, name, value, true, true, startMap);
    }

    public BoreSql andStartsWith(String field, Object value, boolean match) {
        return this.doLike(" AND ", field, (String)null, value, match, true, startMap);
    }

    public BoreSql andStartsWith(String field, Object value, String name, boolean match) {
        return this.doLike(" AND ", field, name, value, match, true, startMap);
    }

    public BoreSql orStartsWith(String field, Object value) {
        return this.doLike(" OR ", field, (String)null, value, true, true, startMap);
    }

    public BoreSql orStartsWith(String field, Object value, String name) {
        return this.doLike(" OR ", field, name, value, true, true, startMap);
    }

    public BoreSql orStartsWith(String field, Object value, boolean match) {
        return this.doLike(" OR ", field, (String)null, value, match, true, startMap);
    }

    public BoreSql orStartsWith(String field, Object value, String name, boolean match) {
        return this.doLike(" OR ", field, name, value, match, true, startMap);
    }

    public BoreSql notStartsWith(String field, Object value) {
        return this.doLike(" ", field, (String)null, value, true, false, startMap);
    }

    public BoreSql notStartsWith(String field, Object value, String name) {
        return this.doLike(" ", field, name, value, true, false, startMap);
    }

    public BoreSql notStartsWith(String field, Object value, boolean match) {
        return this.doLike(" ", field, (String)null, value, match, false, startMap);
    }

    public BoreSql notStartsWith(String field, Object value, String name, boolean match) {
        return this.doLike(" ", field, name, value, match, false, startMap);
    }

    public BoreSql andNotStartsWith(String field, Object value) {
        return this.doLike(" AND ", field, (String)null, value, true, false, startMap);
    }

    public BoreSql andNotStartsWith(String field, Object value, String name) {
        return this.doLike(" AND ", field, name, value, true, false, startMap);
    }

    public BoreSql andNotStartsWith(String field, Object value, boolean match) {
        return this.doLike(" AND ", field, (String)null, value, match, false, startMap);
    }

    public BoreSql andNotStartsWith(String field, Object value, String name, boolean match) {
        return this.doLike(" AND ", field, name, value, match, false, startMap);
    }

    public BoreSql orNotStartsWith(String field, Object value) {
        return this.doLike(" OR ", field, (String)null, value, true, false, startMap);
    }

    public BoreSql orNotStartsWith(String field, Object value, String name) {
        return this.doLike(" OR ", field, name, value, true, false, startMap);
    }

    public BoreSql orNotStartsWith(String field, Object value, boolean match) {
        return this.doLike(" OR ", field, (String)null, value, match, false, startMap);
    }

    public BoreSql orNotStartsWith(String field, Object value, String name, boolean match) {
        return this.doLike(" OR ", field, name, value, match, false, startMap);
    }

    public BoreSql endsWith(String field, Object value) {
        return this.doLike(" ", field, (String)null, value, true, true, endMap);
    }

    public BoreSql endsWith(String field, Object value, String name) {
        return this.doLike(" ", field, name, value, true, true, endMap);
    }

    public BoreSql endsWith(String field, Object value, boolean match) {
        return this.doLike(" ", field, (String)null, value, match, true, endMap);
    }

    public BoreSql endsWith(String field, Object value, String name, boolean match) {
        return this.doLike(" ", field, name, value, match, true, endMap);
    }

    public BoreSql andEndsWith(String field, Object value) {
        return this.doLike(" AND ", field, (String)null, value, true, true, endMap);
    }

    public BoreSql andEndsWith(String field, Object value, String name) {
        return this.doLike(" AND ", field, name, value, true, true, endMap);
    }

    public BoreSql andEndsWith(String field, Object value, boolean match) {
        return this.doLike(" AND ", field, (String)null, value, match, true, endMap);
    }

    public BoreSql andEndsWith(String field, Object value, String name, boolean match) {
        return this.doLike(" AND ", field, name, value, match, true, endMap);
    }

    public BoreSql orEndsWith(String field, Object value) {
        return this.doLike(" OR ", field, (String)null, value, true, true, endMap);
    }

    public BoreSql orEndsWith(String field, Object value, String name) {
        return this.doLike(" OR ", field, name, value, true, true, endMap);
    }

    public BoreSql orEndsWith(String field, Object value, boolean match) {
        return this.doLike(" OR ", field, (String)null, value, match, true, endMap);
    }

    public BoreSql orEndsWith(String field, Object value, String name, boolean match) {
        return this.doLike(" OR ", field, name, value, match, true, endMap);
    }

    public BoreSql notEndsWith(String field, Object value) {
        return this.doLike(" ", field, (String)null, value, true, false, endMap);
    }

    public BoreSql notEndsWith(String field, Object value, String name) {
        return this.doLike(" ", field, name, value, true, false, endMap);
    }

    public BoreSql notEndsWith(String field, Object value, boolean match) {
        return this.doLike(" ", field, (String)null, value, match, false, endMap);
    }

    public BoreSql notEndsWith(String field, Object value, String name, boolean match) {
        return this.doLike(" ", field, name, value, match, false, endMap);
    }

    public BoreSql andNotEndsWith(String field, Object value) {
        return this.doLike(" AND ", field, (String)null, value, true, false, endMap);
    }

    public BoreSql andNotEndsWith(String field, Object value, String name) {
        return this.doLike(" AND ", field, name, value, true, false, endMap);
    }

    public BoreSql andNotEndsWith(String field, Object value, boolean match) {
        return this.doLike(" AND ", field, (String)null, value, match, false, endMap);
    }

    public BoreSql andNotEndsWith(String field, Object value, String name, boolean match) {
        return this.doLike(" AND ", field, name, value, match, false, endMap);
    }

    public BoreSql orNotEndsWith(String field, Object value) {
        return this.doLike(" OR ", field, (String)null, value, true, false, endMap);
    }

    public BoreSql orNotEndsWith(String field, Object value, String name) {
        return this.doLike(" OR ", field, name, value, true, false, endMap);
    }

    public BoreSql orNotEndsWith(String field, Object value, boolean match) {
        return this.doLike(" OR ", field, (String)null, value, match, false, endMap);
    }

    public BoreSql orNotEndsWith(String field, Object value, String name, boolean match) {
        return this.doLike(" OR ", field, name, value, match, false, endMap);
    }

    public BoreSql likePattern(String field, String pattern) {
        return this.doLikePattern(" ", field, pattern, true, true);
    }

    public BoreSql likePattern(String field, String pattern, boolean match) {
        return this.doLikePattern(" ", field, pattern, match, true);
    }

    public BoreSql andLikePattern(String field, String pattern) {
        return this.doLikePattern(" AND ", field, pattern, true, true);
    }

    public BoreSql andLikePattern(String field, String pattern, boolean match) {
        return this.doLikePattern(" AND ", field, pattern, match, true);
    }

    public BoreSql orLikePattern(String field, String pattern) {
        return this.doLikePattern(" OR ", field, pattern, true, true);
    }

    public BoreSql orLikePattern(String field, String pattern, boolean match) {
        return this.doLikePattern(" OR ", field, pattern, match, true);
    }

    public BoreSql notLikePattern(String field, String pattern) {
        return this.doLikePattern(" ", field, pattern, true, false);
    }

    public BoreSql notLikePattern(String field, String pattern, boolean match) {
        return this.doLikePattern(" ", field, pattern, match, false);
    }

    public BoreSql andNotLikePattern(String field, String pattern) {
        return this.doLikePattern(" AND ", field, pattern, true, false);
    }

    public BoreSql andNotLikePattern(String field, String pattern, boolean match) {
        return this.doLikePattern(" AND ", field, pattern, match, false);
    }

    public BoreSql orNotLikePattern(String field, String pattern) {
        return this.doLikePattern(" OR ", field, pattern, true, false);
    }

    public BoreSql orNotLikePattern(String field, String pattern, boolean match) {
        return this.doLikePattern(" OR ", field, pattern, match, false);
    }

    public BoreSql between(String field, Object startValue, Object endValue) {
        return this.doBetween(" ", field, (String)null, startValue, (String)null, endValue, true);
    }

    public BoreSql between(String field, String startName, Object startValue, String endName, Object endValue) {
        return this.doBetween(" ", field, startName, startValue, endName, endValue, true);
    }

    public BoreSql between(String field, Object startValue, Object endValue, boolean match) {
        return this.doBetween(" ", field, (String)null, startValue, (String)null, endValue, match);
    }

    public BoreSql between(String field, String startName, Object startValue, String endName, Object endValue, boolean match) {
        return this.doBetween(" ", field, startName, startValue, endName, endValue, match);
    }

    public BoreSql andBetween(String field, Object startValue, Object endValue) {
        return this.doBetween(" AND ", field, (String)null, startValue, (String)null, endValue, true);
    }

    public BoreSql andBetween(String field, String startName, Object startValue, String endName, Object endValue) {
        return this.doBetween(" AND ", field, startName, startValue, endName, endValue, true);
    }

    public BoreSql andBetween(String field, Object startValue, Object endValue, boolean match) {
        return this.doBetween(" AND ", field, (String)null, startValue, (String)null, endValue, match);
    }

    public BoreSql andBetween(String field, String startName, Object startValue, String endName, Object endValue, boolean match) {
        return this.doBetween(" AND ", field, startName, startValue, endName, endValue, match);
    }

    public BoreSql orBetween(String field, Object startValue, Object endValue) {
        return this.doBetween(" OR ", field, (String)null, startValue, (String)null, endValue, true);
    }

    public BoreSql orBetween(String field, String startName, Object startValue, String endName, Object endValue) {
        return this.doBetween(" OR ", field, startName, startValue, endName, endValue, true);
    }

    public BoreSql orBetween(String field, Object startValue, Object endValue, boolean match) {
        return this.doBetween(" OR ", field, (String)null, startValue, (String)null, endValue, match);
    }

    public BoreSql orBetween(String field, String startName, Object startValue, String endName, Object endValue, boolean match) {
        return this.doBetween(" OR ", field, startName, startValue, endName, endValue, match);
    }

    public BoreSql in(String field, Object[] values) {
        return this.doIn(" ", field, (String)null, (Object[])values, true, true);
    }

    public BoreSql in(String field, String name, Object[] values) {
        return this.doIn(" ", field, name, values, true, true);
    }

    public BoreSql in(String field, Object[] values, boolean match) {
        return this.doIn(" ", field, (String)null, (Object[])values, match, true);
    }

    public BoreSql in(String field, String name, Object[] values, boolean match) {
        return this.doIn(" ", field, name, values, match, true);
    }

    public BoreSql in(String field, Collection<?> values) {
        return this.doIn(" ", field, (String)null, (Collection)values, true, true);
    }

    public BoreSql in(String field, String name, Collection<?> values) {
        return this.doIn(" ", field, name, values, true, true);
    }

    public BoreSql in(String field, Collection<?> values, boolean match) {
        return this.doIn(" ", field, (String)null, (Collection)values, match, true);
    }

    public BoreSql in(String field, String name, Collection<?> values, boolean match) {
        return this.doIn(" ", field, name, values, match, true);
    }

    public BoreSql andIn(String field, Object[] values) {
        return this.doIn(" AND ", field, (String)null, (Object[])values, true, true);
    }

    public BoreSql andIn(String field, String name, Object[] values) {
        return this.doIn(" AND ", field, name, values, true, true);
    }

    public BoreSql andIn(String field, Object[] values, boolean match) {
        return this.doIn(" AND ", field, (String)null, (Object[])values, match, true);
    }

    public BoreSql andIn(String field, String name, Object[] values, boolean match) {
        return this.doIn(" AND ", field, name, values, match, true);
    }

    public BoreSql andIn(String field, Collection<?> values) {
        return this.doIn(" AND ", field, (String)null, (Collection)values, true, true);
    }

    public BoreSql andIn(String field, String name, Collection<?> values) {
        return this.doIn(" AND ", field, name, values, true, true);
    }

    public BoreSql andIn(String field, Collection<?> values, boolean match) {
        return this.doIn(" AND ", field, (String)null, (Collection)values, match, true);
    }

    public BoreSql andIn(String field, String name, Collection<?> values, boolean match) {
        return this.doIn(" AND ", field, name, values, match, true);
    }

    public BoreSql orIn(String field, Object[] values) {
        return this.doIn(" OR ", field, (String)null, (Object[])values, true, true);
    }

    public BoreSql orIn(String field, String name, Object[] values) {
        return this.doIn(" OR ", field, name, values, true, true);
    }

    public BoreSql orIn(String field, Object[] values, boolean match) {
        return this.doIn(" OR ", field, (String)null, (Object[])values, match, true);
    }

    public BoreSql orIn(String field, String name, Object[] values, boolean match) {
        return this.doIn(" OR ", field, name, values, match, true);
    }

    public BoreSql orIn(String field, Collection<?> values) {
        return this.doIn(" OR ", field, (String)null, (Collection)values, true, true);
    }

    public BoreSql orIn(String field, String name, Collection<?> values) {
        return this.doIn(" OR ", field, name, values, true, true);
    }

    public BoreSql orIn(String field, Collection<?> values, boolean match) {
        return this.doIn(" OR ", field, (String)null, (Collection)values, match, true);
    }

    public BoreSql orIn(String field, String name, Collection<?> values, boolean match) {
        return this.doIn(" OR ", field, name, values, match, true);
    }

    public BoreSql notIn(String field, Object[] values) {
        return this.doIn(" ", field, (String)null, (Object[])values, true, false);
    }

    public BoreSql notIn(String field, String name, Object[] values) {
        return this.doIn(" ", field, name, values, true, false);
    }

    public BoreSql notIn(String field, Object[] values, boolean match) {
        return this.doIn(" ", field, (String)null, (Object[])values, match, false);
    }

    public BoreSql notIn(String field, String name, Object[] values, boolean match) {
        return this.doIn(" ", field, name, values, match, false);
    }

    public BoreSql notIn(String field, Collection<?> values) {
        return this.doIn(" ", field, (String)null, (Collection)values, true, false);
    }

    public BoreSql notIn(String field, String name, Collection<?> values) {
        return this.doIn(" ", field, name, values, true, false);
    }

    public BoreSql notIn(String field, Collection<?> values, boolean match) {
        return this.doIn(" ", field, (String)null, (Collection)values, match, false);
    }

    public BoreSql notIn(String field, String name, Collection<?> values, boolean match) {
        return this.doIn(" ", field, name, values, match, false);
    }

    public BoreSql andNotIn(String field, Object[] values) {
        return this.doIn(" AND ", field, (String)null, (Object[])values, true, false);
    }

    public BoreSql andNotIn(String field, String name, Object[] values) {
        return this.doIn(" AND ", field, name, values, true, false);
    }

    public BoreSql andNotIn(String field, Object[] values, boolean match) {
        return this.doIn(" AND ", field, (String)null, (Object[])values, match, false);
    }

    public BoreSql andNotIn(String field, String name, Object[] values, boolean match) {
        return this.doIn(" AND ", field, name, values, match, false);
    }

    public BoreSql andNotIn(String field, Collection<?> values) {
        return this.doIn(" AND ", field, (String)null, (Collection)values, true, false);
    }

    public BoreSql andNotIn(String field, String name, Collection<?> values) {
        return this.doIn(" AND ", field, name, values, true, false);
    }

    public BoreSql andNotIn(String field, Collection<?> values, boolean match) {
        return this.doIn(" AND ", field, (String)null, (Collection)values, match, false);
    }

    public BoreSql andNotIn(String field, String name, Collection<?> values, boolean match) {
        return this.doIn(" AND ", field, name, values, match, false);
    }

    public BoreSql orNotIn(String field, Object[] values) {
        return this.doIn(" OR ", field, (String)null, (Object[])values, true, false);
    }

    public BoreSql orNotIn(String field, String name, Object[] values) {
        return this.doIn(" OR ", field, name, values, true, false);
    }

    public BoreSql orNotIn(String field, Object[] values, boolean match) {
        return this.doIn(" OR ", field, (String)null, (Object[])values, match, false);
    }

    public BoreSql orNotIn(String field, String name, Object[] values, boolean match) {
        return this.doIn(" OR ", field, name, values, match, false);
    }

    public BoreSql orNotIn(String field, Collection<?> values) {
        return this.doIn(" OR ", field, (String)null, (Collection)values, true, false);
    }

    public BoreSql orNotIn(String field, String name, Collection<?> values) {
        return this.doIn(" OR ", field, name, values, true, false);
    }

    public BoreSql orNotIn(String field, Collection<?> values, boolean match) {
        return this.doIn(" OR ", field, (String)null, (Collection)values, match, false);
    }

    public BoreSql orNotIn(String field, String name, Collection<?> values, boolean match) {
        return this.doIn(" OR ", field, name, values, match, false);
    }

    public BoreSql isNull(String field) {
        return this.doIsNull(" ", field, true, true);
    }

    public BoreSql isNull(String field, boolean match) {
        return this.doIsNull(" ", field, match, true);
    }

    public BoreSql andIsNull(String field) {
        return this.doIsNull(" AND ", field, true, true);
    }

    public BoreSql andIsNull(String field, boolean match) {
        return this.doIsNull(" AND ", field, match, true);
    }

    public BoreSql orIsNull(String field) {
        return this.doIsNull(" OR ", field, true, true);
    }

    public BoreSql orIsNull(String field, boolean match) {
        return this.doIsNull(" OR ", field, match, true);
    }

    public BoreSql isNotNull(String field) {
        return this.doIsNull(" ", field, true, false);
    }

    public BoreSql isNotNull(String field, boolean match) {
        return this.doIsNull(" ", field, match, false);
    }

    public BoreSql andIsNotNull(String field) {
        return this.doIsNull(" AND ", field, true, false);
    }

    public BoreSql andIsNotNull(String field, boolean match) {
        return this.doIsNull(" AND ", field, match, false);
    }

    public BoreSql orIsNotNull(String field) {
        return this.doIsNull(" OR ", field, true, false);
    }

    public BoreSql orIsNotNull(String field, boolean match) {
        return this.doIsNull(" OR ", field, match, false);
    }
}
