//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.redis.lock;

/**
 * @author heshuang
 */
public interface IDistributedLock {
    boolean lock(String var1);

    boolean lock(String var1, int var2);

    boolean lock(String var1, int var2, long var3);

    boolean lock(String var1, long var2);

    boolean lock(String var1, long var2, int var4);

    boolean lock(String var1, long var2, int var4, long var5);

    boolean releaseLock(String var1);
}
