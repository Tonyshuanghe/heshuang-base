package com.heshuang.core.base.supports.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executor;

/**
 * @author heshuang
 * @version v1.0
 * CreationTime: - 2022/4/21 17:24
 * Description: 定时支持
 */
@EnableScheduling
@Configuration
public class ScheduleSupports implements SchedulingConfigurer {
    @Autowired
    @Qualifier("taskExecutorService")
    private Executor taskExecutorService;


    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.setScheduler(taskExecutorService);
    }

}
