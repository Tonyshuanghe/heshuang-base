//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.redis;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(
    prefix = "heshuang.lock"
)
@Component
@Data
@Slf4j
public class DistributedLockProperties {
    private String lockType = "redis";
    private Integer timeOut = 30000;
    private Integer retryTimes = 5;
    private Integer sleep = 500;
    private String key = "heshuang:lock:";




}
