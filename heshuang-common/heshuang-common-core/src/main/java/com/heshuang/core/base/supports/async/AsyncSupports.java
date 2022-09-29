package com.heshuang.core.base.supports.async;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.Executor;

/**
 * @author heshuang
 * @version v1.0
 * CreationTime: - 2022/4/21 17:24
 * Description: 定时支持
 */
@EnableAsync
@Configuration
public class AsyncSupports implements AsyncConfigurer {
    @Autowired
    @Qualifier("asyncExecutor")
    private Executor asyncExecutor;

    @Override
    public Executor getAsyncExecutor() {
        return asyncExecutor;
    }
}
