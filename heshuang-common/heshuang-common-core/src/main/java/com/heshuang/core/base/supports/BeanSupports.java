package com.heshuang.core.base.supports;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.IdUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.concurrent.ExecutorService;

/**
 * @author heshuang
 * @version v1.0
 * CreationTime: - 2022/4/15 14:08
 * Description: bean创建
 */
@Configuration
@ComponentScan({"com.heshuang.core.base.supports"})
public class BeanSupports {

    @Value("${workerId:0}")
    private Long workerId;

    @Bean
    @Primary
    @ConditionalOnMissingBean(Snowflake.class)
    public Snowflake getSnowflake() {
        return IdUtil.getSnowflake(workerId);
    }

    /**
     * 自定义消费队列线程池
     *
     * @return
     */
    @Bean(value = "taskExecutorService")
    public ExecutorService getTaskExecutorService() {
       return ThreadUtil.createScheduledExecutor(10);
    }

    /**
     * 自定义消费队列线程池
     *
     * @return
     */
    @Bean(value = "asyncExecutor")
    public ExecutorService getAsyncExecutor() {
        return ThreadUtil.newExecutor(10);
    }
}
