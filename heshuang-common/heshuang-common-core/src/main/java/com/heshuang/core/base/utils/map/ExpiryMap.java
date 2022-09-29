package com.heshuang.core.base.utils.map;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description: 带过期时间的map  每5分钟 一次回收  线程安全
 * @Author: zcm
 * @Date: 2020/4/29 16:03  用时3个小时  2020/4/30 10:16 完成
 */
public class ExpiryMap<K,V> implements Map<K,V>{

    private ConcurrentHashMap workMap ;
    private ConcurrentHashMap expiryMap ;
    /**
     * 默认保存时间2分钟
     */
    private long EXPIRYTIME =1000 * 60 * 2;
    /**
     *  circulation 循环时间  默认5分钟
     */
    private long CIRCULATIONTIME = 1000*60*5;
    /**
     *   delay 启动延迟时间  默认1分钟
     */
    private long DELAY = 1000*60;

    public ExpiryMap() {
        this(16,0);
    }
    /**
     * 单位秒
     * @param expiryTime
     */
    public ExpiryMap(long expiryTime) {
        this(16,expiryTime);
    }
    /**
     * 单位秒
     * @param initialCapacity
     * @param expiryTime
     */
    public ExpiryMap(int initialCapacity, long expiryTime) {
       if(expiryTime>0)
           this.EXPIRYTIME = expiryTime *1000;
        workMap =new ConcurrentHashMap(initialCapacity);
        expiryMap =new ConcurrentHashMap(initialCapacity);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("The Expiry Map size is "+size());
            }
        },DELAY,CIRCULATIONTIME);
    }
    /**
     * 使用 全局过期时间
     * @param key
     * @param value
     * @return
     */
    @Override
    public V put(K key, V value) {
        expiryMap.put(key,System.currentTimeMillis()+EXPIRYTIME);
        return (V)workMap.put(key, value);
    }
    /**
     * 使用 自定义过期时间 单位秒
     * @param key
     * @param value
     * @return
     */
    public V put(K key, V value,long exrity) {
        expiryMap.put(key,System.currentTimeMillis()+ exrity*1000);
        return (V) workMap.put(key, value);
    }

    private void removeExpiryKeys(){
        expiryMap.keySet().forEach(key->{
            checkExpiry((K) key,true);
        });
    }
    /**
     * 是否过期判断函数
     * @param key
     * @param isDelete
     * @return 过期true  不过期false
     */
    private boolean checkExpiry(K key,boolean isDelete){
        Object timeObject =expiryMap.get(key);
        if(timeObject==null){
            return true;
        }
        long setTime = (long) timeObject;
        boolean isExpiry = System.currentTimeMillis()-setTime>=0;
        if(isExpiry){
            if(isDelete){
                expiryMap.remove(key);
                workMap.remove(key);
            }
            return true;
        }
        return false;
    }

    @Override
    public V get(Object key) {
        boolean isExpiry = checkExpiry((K)key, true);
        if(isExpiry){
            return null;
        }
        return (V)workMap.get(key);
    }

    @Override
    public int size() {
        removeExpiryKeys();
        return workMap.size();
    }
    @Override
    public boolean isEmpty() {
        removeExpiryKeys();
        return workMap.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        removeExpiryKeys();
        return workMap.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        removeExpiryKeys();
        return workMap.containsValue(value);
    }

    @Override
    public V remove(Object key) {
        expiryMap.remove(key);
        return (V)workMap.remove(key);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        m.entrySet().forEach(en->{
            expiryMap.put(en.getKey(),System.currentTimeMillis()+EXPIRYTIME);
            workMap.put(en.getKey(),en.getValue());
        });
    }

    @Override
    public void clear() {
        expiryMap.clear();
        workMap.clear();
    }

    @Override
    public Set<K> keySet() {
        removeExpiryKeys();
        return workMap.keySet();
    }

    @Override
    public Collection<V> values() {
        removeExpiryKeys();
        return workMap.values();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        removeExpiryKeys();
        return workMap.entrySet();
    }

    public void setCIRCULATIONTIME(long CIRCULATIONTIME) {
        this.CIRCULATIONTIME = CIRCULATIONTIME;
    }

    public void setDELAY(long DELAY) {
        this.DELAY = DELAY;
    }

    public long getCIRCULATIONTIME() {
        return CIRCULATIONTIME;
    }

    public long getDELAY() {
        return DELAY;
    }

    public static void main(String[] args) throws InterruptedException {
        ExpiryMap<String, String> map = new ExpiryMap<>();
        map.put("1","1",3);
        map.put("2","2",10);
        map.put("3","3");
        System.out.println( map.get("1"));
        System.out.println( map.get("2"));
        System.out.println( map.get("3"));
        Thread.sleep(10000);
        map.size();
        System.out.println( map.get("1"));
        System.out.println( map.get("2"));
        System.out.println( map.get("3"));
        map.put("1","1");
        map.put("2","2");
        map.put("3","3");
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("4","4");
        hashMap.put("5","5");
        map.putAll(hashMap);
        System.out.println( map.size());
        System.out.println( map.values());
        System.out.println( map.entrySet());
        System.out.println( map.isEmpty());
        System.out.println( map.containsKey("1"));
        System.out.println( map.containsValue("2"));
        System.out.println( map.remove("1"));
        System.out.println( map.get("1"));
    }
}

