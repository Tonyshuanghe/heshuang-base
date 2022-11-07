//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.dataflow.core.filters;


import com.heshuang.dataflow.core.IDataFilter;

public abstract class AbstractDataFilter implements IDataFilter {
    private String name;

    public AbstractDataFilter() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
