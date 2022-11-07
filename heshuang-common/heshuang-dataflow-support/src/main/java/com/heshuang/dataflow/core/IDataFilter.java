//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.dataflow.core;

import java.util.List;

public interface IDataFilter {
    List<Object> filter(List<Object> var1);

    default String getName() {
        return this.getClass().getName();
    }

    default void setName(String name) {
    }
}
