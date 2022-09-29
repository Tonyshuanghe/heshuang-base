package com.heshuang.redis;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import com.heshuang.redis.lock.IDistributedLock;
import com.heshuang.redis.lock.RedisDistributedLock;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author heshuang
 * @version v1.0
 * CreationTime: - 2022/3/29 14:38
 * Description: redis
 */
@Configuration
@EnableCaching
@Import({DistributedLockProperties.class})
public class RedisSupports {

    @Bean
    public IDistributedLock redisDistributedLock(RedisTemplate<?, ?> redisTemplate, DistributedLockProperties distributedLockProperties) {
        return "redis".equals(distributedLockProperties.getLockType()) ? new RedisDistributedLock(redisTemplate, distributedLockProperties) : null;
    }


    @Bean
    public RedisMessageListenerContainer configRedisMessageListenerContainer(RedisConnectionFactory connectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        return container;
    }

    /**
     * Description redisCache 缓存对象需要实现序列化接口
     * Author heshuang
     * Date 2022/4/6 15:13
     *
     * @param redisConnectionFactory
     * @return org.springframework.cache.CacheManager
     */
    @Bean
    @Primary
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                // 默认30s
                .entryTtl(Duration.ofSeconds(60L))
                .computePrefixWith(cacheName -> "caching:" + cacheName)
                .serializeKeysWith(SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(SerializationPair.fromSerializer(new GenericFastJsonRedisSerializer()));

        Map<String, RedisCacheConfiguration> initialCacheConfiguration = new HashMap<String, RedisCacheConfiguration>(8) {{
            //默认1天
            put("treeDeviceInfo", RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofDays(1)));
        }};

        RedisCacheManager redisCacheManager = RedisCacheManager.builder(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory))
                .cacheDefaults(defaultCacheConfig)
                .withInitialCacheConfigurations(initialCacheConfiguration)
                .build();
        return redisCacheManager;
    }


}
