package com.heshuang.dataflow.support;

import com.google.common.collect.Maps;
import com.heshuang.dataflow.core.IDataSource;
import java.util.Map;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SchedulerProcessor {
  private static final Logger log = LoggerFactory.getLogger(SchedulerProcessor.class);
  private Map<String, IDataSource> dataSource;
  private static SchedulerProcessor instance;

  public SchedulerProcessor() {
  }

  public static SchedulerProcessor newInstance() {
    if (instance == null) {
      Class var0 = SchedulerProcessor.class;
      synchronized(SchedulerProcessor.class) {
        if (instance == null) {
          instance = new SchedulerProcessor();
          instance.init();
        }
      }
    }

    return instance;
  }

  public void init() {
    this.dataSource = Maps.newConcurrentMap();
  }

  public void putDataSource(String taskId, IDataSource dataSource) {
    this.dataSource.put(taskId, dataSource);
  }

  public void removeDataSource(String taskId) {
    this.dataSource.remove(taskId);
  }

  public void on(String taskId) {
    IDataSource iDataSource = (IDataSource)this.dataSource.get(taskId);
    if (iDataSource != null) {
      iDataSource.on((Supplier)null);
    }

  }
}
