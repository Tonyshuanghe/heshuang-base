//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.logger.handler.info;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ParserInfo {
    private Object target;
    private Object value;
    private String fieldName;


    public static ParserInfo build(Object target, Object value, String fieldName) {
        return new ParserInfo(target, value, fieldName);
    }
}
