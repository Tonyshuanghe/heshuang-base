//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.core.base.geo;

import com.google.common.collect.Maps;
import java.util.Map;

public class CellNo {
    private Map<Integer, CellNoScope> scopes = Maps.newHashMap();
    private String code;
    private Integer ptr;
    private Integer hPtr;

    public void append(String codePart) {
        this.code = String.format("%s%s", this.code, codePart);
    }

    public CellNoScope getScope(int level) {
        return (CellNoScope)this.scopes.get(level);
    }

    public void putScope(Integer level, CellNoScope scope) {
        this.setPtr(level);
        this.scopes.put(level, scope);
    }

    public void putScope(CellNoScope scope) {
        this.setPtr(scope.getLevel());
        this.scopes.put(scope.getLevel(), scope);
    }

    public boolean hasHptr() {
        return this.hPtr != null && this.hPtr > 0;
    }

    public CellNo() {
    }

    public Map<Integer, CellNoScope> getScopes() {
        return this.scopes;
    }

    public String getCode() {
        return this.code;
    }

    public Integer getPtr() {
        return this.ptr;
    }

    public Integer getHPtr() {
        return this.hPtr;
    }

    public void setScopes(Map<Integer, CellNoScope> scopes) {
        this.scopes = scopes;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setPtr(Integer ptr) {
        this.ptr = ptr;
    }

    public void setHPtr(Integer hPtr) {
        this.hPtr = hPtr;
    }


}
