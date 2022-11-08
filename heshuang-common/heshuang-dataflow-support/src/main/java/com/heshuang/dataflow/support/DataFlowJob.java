package com.heshuang.dataflow.support;

import org.apache.commons.lang3.StringUtils;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class DataFlowJob implements Job {
  @Override
  public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
    JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
    if (jobDataMap != null && jobDataMap.containsKey("taskId")) {
      String taskId = jobDataMap.getString("taskId");
      if (StringUtils.isNotBlank(taskId)) {
        SchedulerProcessor.newInstance().on(taskId);
      }
    }

  }
}
