//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.dict.utils;

import com.google.common.collect.Lists;
import com.heshuang.core.base.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.Map.Entry;

public class CommonUtils {
    private static final Logger log = LoggerFactory.getLogger(CommonUtils.class);

    public CommonUtils() {
    }

    public static int[] int2BinArray(int intValue) {
        int[] zero = new int[]{0};
        int[] max = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        if (intValue == 0) {
            return zero;
        } else {
            int c = 0;

            for (int i = 0; i < 32; ++i) {
                int a = intValue & 1 << i;
                if (a != 0) {
                    ++c;
                    max[c - 1] = a;
                }
            }

            int[] res = new int[c];
            System.arraycopy(max, 0, res, 0, c);
            return res;
        }
    }

    public static long[] int2BinArray(long longValue) {
        long[] zero = new long[]{0L};
        long[] max = new long[]{0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L};
        if (longValue == 0L) {
            return zero;
        } else {
            long a = 0L;
            int c = 0;

            for (int i = 0; i < 32; ++i) {
                a = longValue & (long) (1 << i);
                if (a != 0L) {
                    ++c;
                    max[c - 1] = a;
                }
            }

            long[] res = new long[c];
            System.arraycopy(max, 0, res, 0, c);
            return res;
        }
    }

    public static boolean intOnBin(long a, long b) {
        return (a & b) == b;
    }

    public static List<Long> asList(long[] arrs) {
        List<Long> newStrList = new ArrayList();
        if (arrs == null) {
            return null;
        } else {
            long[] var2 = arrs;
            int var3 = arrs.length;

            for (int var4 = 0; var4 < var3; ++var4) {
                Long obj = var2[var4];
                newStrList.add(obj);
            }

            return newStrList;
        }
    }

    public static boolean isNotEmpty(Collection collection) {
        return collection != null && !collection.isEmpty();
    }

    public static boolean isEmpty(Collection collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isEmpty(Map collection) {
        return collection == null || collection.isEmpty();
    }

    public static <K, V> List<K> getKeyByValue(Map<K, V> map, V v) {
        List<K> keys = Lists.newArrayList();
        Iterator var3 = map.entrySet().iterator();

        while (var3.hasNext()) {
            Entry<K, V> m = (Entry) var3.next();
            if (m != null && m.getValue() != null && v != null && String.valueOf(m.getValue()).equals(String.valueOf(v))) {
                keys.add(m.getKey());
            }
        }

        return keys;
    }


    public static void notNull(Object obj, String msg) {
        if (obj == null) {
            throw BusinessException.of(msg);
        }
    }

    public static void notBlank(String str, String msg) {
        if (StringUtils.isEmpty(str)) {
            throw BusinessException.of(msg);
        }
    }

    public static void notEmpty(Collection collection, String msg) {
        if (isEmpty(collection)) {
            throw BusinessException.of(msg);
        }
    }

    public static void notEmpty(Map collection, String msg) {
        if (isEmpty(collection)) {
            throw BusinessException.of(msg);
        }
    }

    public static void notFalse(Boolean flag, String msg) {
        if (flag == null || flag) {
            throw BusinessException.of(msg);
        }
    }

    public static void notTrue(Boolean flag, String msg) {
        if (flag == null || !flag) {
            throw BusinessException.of(msg);
        }
    }

}
