//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.dataflow.core;

import com.google.common.collect.Lists;
import com.heshuang.dataflow.core.context.DataContextHolder;
import com.heshuang.dataflow.utils.EventHandleUtil;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class DataFilterProxy {
    private IDataFilter dataFilter;
    private String name;
    private Boolean isSimple = true;
    private DataFilterProxy nextFilter;
    private List<DataFilterProxy> nextFilters = Lists.newArrayList();

    public List<Object> filter(List<Object> args) {
        List<Object> handledData = this.dataFilter.filter(args);
        DataContextHolder.getContext().setData(handledData);
        DataContextHolder.getContext().setCurFlowName(this.dataFilter.getName());
        if (!this.complate()) {
            if (this.isSimple) {
                ((DataFilterProxy)this.nextFilters.get(0)).filter(handledData);
            } else {
                handledData = EventHandleUtil.or(this.nextFilters);
                if (this.nextFilter != null) {
                    this.nextFilter.filter(handledData);
                }
            }
        }

        return handledData;
    }

    public DataFilterProxy and(IDataFilter dataFilter) {
        this.nextFilter = wrap(dataFilter);
        return this.nextFilter;
    }

    public DataFilterProxy reNext(IDataFilter... nextFilter) {
        this.nextFilter = null;
        this.nextFilters = Lists.newArrayList();
        return this.next(nextFilter);
    }

    public DataFilterProxy next(IDataFilter... nextFilter) {
        if (nextFilter != null && nextFilter.length > 0) {
            if (nextFilter.length > 1) {
                this.isSimple = false;
            } else {
                this.nextFilter = wrap(nextFilter[0]);
            }

            this.nextFilters.addAll((Collection)Lists.newArrayList(nextFilter).stream().map((it) -> {
                return wrap(it);
            }).collect(Collectors.toList()));
        }

        return this.nextFilter;
    }

    public boolean complate() {
        return this.nextFilters.isEmpty();
    }

    public static DataFilterProxy wrap(IDataFilter dataFilter) {
        DataFilterProxy dataFilterProxy = new DataFilterProxy(dataFilter);
        dataFilterProxy.setName(dataFilter.getName());
        return dataFilterProxy;
    }

    public DataFilterProxy(IDataFilter dataFilter) {
        this.dataFilter = dataFilter;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSimple() {
        return this.isSimple;
    }
}