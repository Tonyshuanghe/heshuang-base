//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.redis.lock;



import com.heshuang.redis.DistributedLockProperties;
import io.lettuce.core.RedisFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.Expiration;

import java.nio.charset.Charset;
import java.util.UUID;
import java.util.concurrent.ExecutionException;


public class RedisDistributedLock implements IDistributedLock {
    private static final Logger log = LoggerFactory.getLogger(RedisDistributedLock.class);
    private RedisTemplate<?, ?> redisTemplate;
    private ThreadLocal<String> lockFlag = new ThreadLocal();
    public static final String UNLOCK_LUA;
    private DistributedLockProperties distributedLockProperties;

    public RedisDistributedLock(RedisTemplate<?, ?> redisTemplate, DistributedLockProperties distributedLockProperties) {
        this.redisTemplate = redisTemplate;
        this.distributedLockProperties = distributedLockProperties;
    }
    @Override
    public boolean lock(String key) {
        return this.lock(key, (long)this.distributedLockProperties.getTimeOut(), this.distributedLockProperties.getRetryTimes(), (long)this.distributedLockProperties.getSleep());
    }
    @Override
    public boolean lock(String key, int retryTimes) {
        return this.lock(key, (long)this.distributedLockProperties.getTimeOut(), retryTimes, (long)this.distributedLockProperties.getSleep());
    }
    @Override
    public boolean lock(String key, int retryTimes, long sleepMillis) {
        return this.lock(key, (long)retryTimes, this.distributedLockProperties.getRetryTimes(), sleepMillis);
    }
    @Override
    public boolean lock(String key, long expire) {
        return this.lock(key, expire, this.distributedLockProperties.getRetryTimes(), (long)this.distributedLockProperties.getSleep());
    }
    @Override
    public boolean lock(String key, long expire, int retryTimes) {
        return this.lock(key, expire, retryTimes, (long)this.distributedLockProperties.getSleep());
    }
    @Override
    public boolean lock(String key, long expire, int retryTimes, long sleepMillis) {
        boolean result;
        for(result = this.setRedis(key, expire); !result && retryTimes-- > 0; result = this.setRedis(key, expire)) {
            try {
                log.debug("lock failed, retrying..." + retryTimes);
                Thread.sleep(sleepMillis);
            } catch (InterruptedException var9) {
                return false;
            }
        }

        return result;
    }

    private boolean setRedis(String key, long expire) {
        try {
            RedisCallback<Boolean> callback = (connection) -> {
                String uuid = UUID.randomUUID().toString();
                this.lockFlag.set("uuid");
                uuid = "lockFlag";
                return connection.set(key.getBytes(Charset.forName("UTF-8")), uuid.getBytes(Charset.forName("UTF-8")), Expiration.seconds(expire), RedisStringCommands.SetOption.SET_IF_ABSENT);
            };
            return (Boolean)this.redisTemplate.execute(callback);
        } catch (Exception var5) {
            log.error("set redis occured an exception", var5);
            return false;
        }
    }

    @Override
    public boolean releaseLock(String key) {
        try {
            RedisCallback<Boolean> callback = (connection) -> {
                return (Boolean)connection.eval(UNLOCK_LUA.getBytes(), ReturnType.BOOLEAN, 1, new byte[][]{key.getBytes(Charset.forName("UTF-8")), "lockFlag".getBytes(Charset.forName("UTF-8"))});
            };
            return (Boolean)this.redisTemplate.execute(callback);
        } catch (Exception var3) {
            log.error("release lock occured an exception", var3);
            return false;
        }
    }

    private Long getEvalResult(RedisFuture future, RedisConnection connection) {
        try {
            Object o = future.get();
            return (Long)o;
        } catch (ExecutionException | InterruptedException var4) {
            log.error("Future get failed, trying to close connection.", var4);
            this.closeConnection(connection);
            return 0L;
        }
    }

    private void closeConnection(RedisConnection connection) {
        try {
            connection.close();
        } catch (Exception var3) {
            log.error("close connection fail.", var3);
        }

    }

    static {
        StringBuilder sb = new StringBuilder();
        sb.append("if redis.call(\"get\",KEYS[1]) == ARGV[1] ");
        sb.append("then ");
        sb.append("    return redis.call(\"del\",KEYS[1]) ");
        sb.append("else ");
        sb.append("    return 0 ");
        sb.append("end ");
        UNLOCK_LUA = sb.toString();
    }
}
