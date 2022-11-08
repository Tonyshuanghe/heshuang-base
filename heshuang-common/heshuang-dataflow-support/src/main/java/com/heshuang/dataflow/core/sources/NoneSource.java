package com.heshuang.dataflow.core.sources;


import java.util.List;
import java.util.function.Supplier;

import com.heshuang.dataflow.core.DataFilterProxy;
import com.heshuang.dataflow.core.IDataSource;
import com.heshuang.dataflow.core.context.DataContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NoneSource implements IDataSource {
  private static final Logger log = LoggerFactory.getLogger(NoneSource.class);
  private DataFilterProxy dataFilterProxy;

  public NoneSource() {
  }
  @Override
  public List on(Supplier<List<Object>> supplier) {
    if (this.dataFilterProxy != null) {
      try {
        List list = (List)supplier.get();
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
  }
  @Override
  public void stop() {
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

