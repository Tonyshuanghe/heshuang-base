//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.jpa.plugs;

import com.heshuang.jpa.plugs.model.SqlInfo;

import java.util.Map;

public final class BoreQueryInfo {
    private Map<String, Object> contextParams;
    private SqlInfo sqlInfo;
    private String querySql;
    private static final ThreadLocal<BoreQueryInfo> boreThreadLocal = new ThreadLocal();

    public BoreQueryInfo() {
    }

    public static BoreQueryInfo getInstance() {
        BoreQueryInfo boreQueryInfo = (BoreQueryInfo)boreThreadLocal.get();
        if (boreQueryInfo == null) {
            boreQueryInfo = new BoreQueryInfo();
            boreThreadLocal.set(boreQueryInfo);
        }

        return boreQueryInfo;
    }

    public static BoreQueryInfo getLocalThreadInstance() {
        return (BoreQueryInfo)boreThreadLocal.get();
    }

    public void remove() {
        boreThreadLocal.remove();
    }

    public Map<String, Object> getContextParams() {
        return this.contextParams;
    }

    public SqlInfo getSqlInfo() {
        return this.sqlInfo;
    }

    public String getQuerySql() {
        return this.querySql;
    }

    public void setContextParams(Map<String, Object> contextParams) {
        this.contextParams = contextParams;
    }

    public void setSqlInfo(SqlInfo sqlInfo) {
        this.sqlInfo = sqlInfo;
    }

    public void setQuerySql(String querySql) {
        this.querySql = querySql;
    }
}
