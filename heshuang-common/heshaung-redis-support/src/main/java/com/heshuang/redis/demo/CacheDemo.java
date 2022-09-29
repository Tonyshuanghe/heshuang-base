package com.heshuang.redis.demo;

import com.google.common.collect.Lists;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author heshuang
 * @version v1.0
 * CreationTime: - 2022/9/23 11:07
 * Description: 测试
 */
@Component
public class CacheDemo {

    @Cacheable(key = "'treeDeviceInfo'", cacheNames = "treeDeviceInfo")
    public List<String> getTree(){
        return Lists.newArrayList("treeDeviceInfo1,treeDeviceInfo2");
    }

    @Cacheable(key = "'treeDeviceInfo232'", cacheNames = "treeDeviceInfo231")
    public List<String> getTree1(){
        return Lists.newArrayList("treeDeviceInfo3,treeDeviceInfo4");
    }

    @CacheEvict(key = "'treeDeviceInfo232'", cacheNames = "treeDeviceInfo231")
    public void cacheEvict(){
    }
}
