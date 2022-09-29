//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.core.base.exception;


public class MQAckNotFoundException extends BusinessException {
    private String message;

    public MQAckNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}
