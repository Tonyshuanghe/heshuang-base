package com.heshuang.businessstart.supports.mp;

import cn.hutool.core.lang.Snowflake;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author heshuang
 */
@Component
public class CustomIdGenerator implements IdentifierGenerator {

    @Autowired
    private Snowflake snowflake;

    @Override
    public Long nextId(Object entity) {
        return snowflake.nextId();
    }
}