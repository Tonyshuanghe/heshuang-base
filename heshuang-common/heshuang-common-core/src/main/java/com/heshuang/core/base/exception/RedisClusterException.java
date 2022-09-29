package com.heshuang.core.base.exception;

public class RedisClusterException extends IllegalArgumentException {
    private String message;

    public RedisClusterException(String message) {
        super(message);
        this.message = message;
    }
}
