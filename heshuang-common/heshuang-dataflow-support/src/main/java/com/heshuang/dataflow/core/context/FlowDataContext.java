//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.dataflow.core.context;

import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

public class FlowDataContext {
    private List<Object> data;
    private String curFlowName;
    private Map<String, Object> request = Maps.newConcurrentMap();
    private List<Object> preData;

    public FlowDataContext() {
    }

    public void put(String key, Object value) {
        this.request.put(key, value);
    }

    public Object get(String key) {
        return this.request.get(key);
    }

    public List<Object> get() {
        return StringUtils.isNotBlank(this.curFlowName) ? (List)this.request.get(this.curFlowName) : null;
    }

    public void setData(List<Object> data) {
        if (this.data != null) {
            this.preData = this.data;
        }

        this.data = data;
        if (StringUtils.isNotBlank(this.curFlowName)) {
            this.put(this.curFlowName, data);
        }

    }

    public List<Object> getData() {
        return this.data;
    }

    public List<Object> getPreData() {
        return this.preData;
    }

    public List<Object> getData(String key) {
        return (List)this.get(key);
    }

    public void setCurFlowName(String name) {
        this.curFlowName = name;
    }

    public Map<String, Object> requestMap() {
        return this.request;
    }

    public void copyRequestMap(Map<String, Object> request) {
        this.request = request;
    }
}
