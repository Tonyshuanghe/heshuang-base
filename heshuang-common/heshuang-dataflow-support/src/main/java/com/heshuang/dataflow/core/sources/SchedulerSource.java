package com.heshuang.dataflow.core.sources;

import java.util.Date;
import java.util.List;
import java.util.function.Supplier;

import cn.hutool.extra.spring.SpringUtil;
import com.heshuang.core.base.exception.BusinessException;
import com.heshuang.core.base.exception.BusinessRtException;
import com.heshuang.core.base.utils.SnowflakeUtils;
import com.heshuang.dataflow.core.DataFilterProxy;
import com.heshuang.dataflow.core.IDataFilter;
import com.heshuang.dataflow.core.IDataSource;
import com.heshuang.dataflow.core.context.DataContextHolder;
import com.heshuang.dataflow.support.DataFlowJob;
import com.heshuang.dataflow.support.SchedulerProcessor;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.ScheduleBuilder;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SchedulerSource implements IDataSource {
  private static final Logger log = LoggerFactory.getLogger(SchedulerSource.class);
  private DataFilterProxy dataFilterProxy;
  private String cron;
  private IDataFilter todo;
  private SchedulerFactory schedulerFactory;
  private Scheduler scheduler;
  private String taskId;

  public SchedulerSource(String cron, IDataFilter todo) {
    this.cron = cron;
    this.todo = todo;
    this.schedulerFactory = (SchedulerFactory) SpringUtil.getBean("dataFlowSchedulerFactory");

    try {
      this.scheduler = this.schedulerFactory.getScheduler();
    } catch (SchedulerException var4) {
      throw BusinessException.of(var4.getMessage(), var4);
    }
  }

  public List on(Supplier<List<Object>> supplier) {
    if (this.dataFilterProxy != null) {
      try {
        List list = this.todo.filter((List)null);
        DataContextHolder.getContext().setData(list);
        DataContextHolder.getContext().setCurFlowName(this.getClass().getName());
        return this.dataFilterProxy.filter(list);
      } catch (Exception var4) {
        log.warn(var4.getMessage(), var4);
      }
    }

    return null;
  }
  @Override
  public void start() {
    try {
      this.taskId = SnowflakeUtils.generateStr();
      SchedulerProcessor.newInstance().putDataSource(this.taskId, this);
      Trigger trigger = TriggerBuilder.newTrigger().withDescription("").withIdentity(this.taskId, "data-flow").startAt(new Date()).withSchedule(CronScheduleBuilder.cronSchedule(this.cron)).build();
      JobDetail jobDetail = JobBuilder.newJob().withIdentity(this.taskId, "data-flow").usingJobData("taskId", this.taskId).ofType(DataFlowJob.class).build();
      this.scheduler.scheduleJob(jobDetail, trigger);
      this.scheduler.start();
    } catch (SchedulerException var3) {
      var3.printStackTrace();
    }

  }
  @Override
  public void stop() {
    SchedulerProcessor.newInstance().removeDataSource(this.taskId);

    try {
      this.scheduler.shutdown();
    } catch (SchedulerException var2) {
      throw BusinessRtException.of(var2.getMessage(), var2);
    }
  }
  @Override
  public void setFilter(DataFilterProxy dataFilterProxy) {
    this.dataFilterProxy = dataFilterProxy;
  }
  @Override
  public Class sourceType() {
    return null;
  }
}