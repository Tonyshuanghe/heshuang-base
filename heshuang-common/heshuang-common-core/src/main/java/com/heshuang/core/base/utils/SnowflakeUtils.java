//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.core.base.utils;

import java.util.Calendar;

/**
 * 获取雪花主键
 * @author heshuang
 */
public class SnowflakeUtils {
    static final long EPOCH;
    static final long workerIdBits = 10L;
    static final long sequenceBits = 12L;
    static final long sequenceMask = 4095L;
    static final long workerIdLeftShiftBits = 12L;
    static final long timestampLeftShiftBits = 22L;
    static final long maxWorkerId = 1024L;
    static long workerId;
    static long sequence;
    static long lastTime;

    public SnowflakeUtils() {
    }

    public static long generate() {
        return generate(0L);
    }

    public static String generateStr() {
        return String.format("%s", generate());
    }

    public static String generateStr(long workerId) {
        return String.format("%s", generate(workerId));
    }

    public static synchronized long generate(long workerId) {
        long currentMillis = System.currentTimeMillis();
        if (currentMillis < lastTime) {
            throw new IllegalArgumentException(String.format("Clock moved backwards, Refusing to generate id for %d milliseconds", lastTime - currentMillis));
        } else {
            if (lastTime == currentMillis) {
                if (0L == (sequence = ++sequence & 4095L)) {
                    currentMillis = waitUntilNextTime(currentMillis);
                }
            } else {
                sequence = 0L;
            }

            lastTime = currentMillis;
            return currentMillis - EPOCH << 22 | workerId << 12 | sequence;
        }
    }

    private static long waitUntilNextTime(long lastTime) {
        long time;
        for(time = System.currentTimeMillis(); time <= lastTime; time = System.currentTimeMillis()) {
        }

        return time;
    }

    public static void main(String[] args) {
        try {
            Long autoId = generate(450100L);
            System.out.println((autoId - 1L >> 32) - 1L);
            System.out.println(autoId - 1L >> 32);
            System.out.println(2147483647);
            System.out.println(Integer.toBinaryString(4095));
            long id = 555962692614410432L;
            System.out.println((id & 9223372036850582000L) >> 22);
            System.out.println(((id & 9223372036850582000L) >> 22) + 1423118109123L);
            System.out.println((id & 2095104L) >> 11);
            System.out.println((id & 2032L) >> 4);
            System.out.println(id & 15L);
        } catch (Throwable var4) {
            throw var4;
        }
    }

    static {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2019, 0, 1);
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        EPOCH = calendar.getTimeInMillis();
    }
}
