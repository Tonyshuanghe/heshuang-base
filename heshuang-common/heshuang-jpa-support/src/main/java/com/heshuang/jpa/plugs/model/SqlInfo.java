//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.jpa.plugs.model;

import java.util.HashMap;
import java.util.Map;

public class SqlInfo {
    private StringBuilder join = new StringBuilder();
    private Map<String, Object> params = new HashMap();
    private String sql;
    private String resultType;
    private boolean prependWhere;

    public SqlInfo() {
    }

    public SqlInfo removeIfExist(String subSql) {
        this.sql = subSql != null && this.sql.contains(subSql) ? this.sql.replaceAll(subSql, "") : this.sql;
        return this;
    }

    public SqlInfo setResultTypeClass(Class<?> resultTypeClass) {
        this.resultType = resultTypeClass.getName();
        return this;
    }

    public StringBuilder getJoin() {
        return this.join;
    }

    public Map<String, Object> getParams() {
        return this.params;
    }

    public String getSql() {
        return this.sql;
    }

    public String getResultType() {
        return this.resultType;
    }

    public boolean isPrependWhere() {
        return this.prependWhere;
    }

    public void setJoin(StringBuilder join) {
        this.join = join;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public void setPrependWhere(boolean prependWhere) {
        this.prependWhere = prependWhere;
    }
}
