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

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof CellNo)) {
            return false;
        } else {
            CellNo other = (CellNo)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label59: {
                    Object this$scopes = this.getScopes();
                    Object other$scopes = other.getScopes();
                    if (this$scopes == null) {
                        if (other$scopes == null) {
                            break label59;
                        }
                    } else if (this$scopes.equals(other$scopes)) {
                        break label59;
                    }

                    return false;
                }

                Object this$code = this.getCode();
                Object other$code = other.getCode();
                if (this$code == null) {
                    if (other$code != null) {
                        return false;
                    }
                } else if (!this$code.equals(other$code)) {
                    return false;
                }

                Object this$ptr = this.getPtr();
                Object other$ptr = other.getPtr();
                if (this$ptr == null) {
                    if (other$ptr != null) {
                        return false;
                    }
                } else if (!this$ptr.equals(other$ptr)) {
                    return false;
                }

                Object this$hPtr = this.getHPtr();
                Object other$hPtr = other.getHPtr();
                if (this$hPtr == null) {
                    if (other$hPtr != null) {
                        return false;
                    }
                } else if (!this$hPtr.equals(other$hPtr)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof CellNo;
    }

    public int hashCode() {
        int PRIME = true;
        int result = 1;
        Object $scopes = this.getScopes();
        int result = result * 59 + ($scopes == null ? 43 : $scopes.hashCode());
        Object $code = this.getCode();
        result = result * 59 + ($code == null ? 43 : $code.hashCode());
        Object $ptr = this.getPtr();
        result = result * 59 + ($ptr == null ? 43 : $ptr.hashCode());
        Object $hPtr = this.getHPtr();
        result = result * 59 + ($hPtr == null ? 43 : $hPtr.hashCode());
        return result;
    }

    public String toString() {
        return "CellNo(scopes=" + this.getScopes() + ", code=" + this.getCode() + ", ptr=" + this.getPtr() + ", hPtr=" + this.getHPtr() + ")";
    }
}
