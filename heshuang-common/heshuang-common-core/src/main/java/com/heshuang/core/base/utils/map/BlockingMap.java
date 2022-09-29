package com.heshuang.core.base.utils.map;

public interface BlockingMap<V> {

    V put(String key, V o) throws InterruptedException;

    V take(String key) throws InterruptedException;

    V poll(String key, long timeout) throws InterruptedException;

}

