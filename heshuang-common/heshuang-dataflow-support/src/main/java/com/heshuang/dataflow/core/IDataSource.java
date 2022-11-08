//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.dataflow.core;

import java.util.List;
import java.util.function.Supplier;

public interface IDataSource {
    List on(Supplier<List<Object>> var1);

    void start();

    void stop();

    void setFilter(DataFilterProxy var1);

    Class sourceType();

    default boolean isActivate() {
        return false;
    }
}
