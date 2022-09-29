//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.jpa.plugs.model;

import java.util.Map;

public final class BuildSource {
    private String namespace;
    private SqlInfo sqlInfo;
    private Object context;
    private String prefix;
    private String symbol;
    private Map<String, Object> others;

    public BuildSource(SqlInfo sqlInfo) {
        this.sqlInfo = sqlInfo;
        this.resetPrefix();
        this.resetSymbol();
    }

    public BuildSource(String namespace, SqlInfo sqlInfo, Object context) {
        this.namespace = namespace;
        this.sqlInfo = sqlInfo;
        this.context = context;
        this.resetPrefix();
        this.resetSymbol();
    }

    public void resetPrefix() {
        this.prefix = " ";
    }

    void resetSymbol() {
        this.symbol = " ";
    }

    public void reset() {
        this.prefix = " ";
        this.symbol = " ";
        this.others = null;
    }

    public String getNamespace() {
        return this.namespace;
    }

    public SqlInfo getSqlInfo() {
        return this.sqlInfo;
    }

    public Object getContext() {
        return this.context;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public String getSymbol() {
        return this.symbol;
    }

    public Map<String, Object> getOthers() {
        return this.others;
    }

    public BuildSource setNamespace(String namespace) {
        this.namespace = namespace;
        return this;
    }

    public BuildSource setSqlInfo(SqlInfo sqlInfo) {
        this.sqlInfo = sqlInfo;
        return this;
    }

    public BuildSource setContext(Object context) {
        this.context = context;
        return this;
    }

    public BuildSource setPrefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    public BuildSource setSymbol(String symbol) {
        this.symbol = symbol;
        return this;
    }

    public BuildSource setOthers(Map<String, Object> others) {
        this.others = others;
        return this;
    }
}
