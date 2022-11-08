//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.dataflow.core.context;

public class DataContextHolder {
    private static final ThreadLocal<FlowDataContext> contextHolder = new ThreadLocal<>();

    public static FlowDataContext getContext() {
        FlowDataContext flowDataContext = contextHolder.get();
        if (flowDataContext == null) {
            flowDataContext = createEmptyContext();
            contextHolder.set(flowDataContext);
        }
        return flowDataContext;
    }

    public static void setContext(FlowDataContext flowDataContext) {
        contextHolder.set(flowDataContext);
    }

    public static void clear() {
        contextHolder.remove();
    }

    public static FlowDataContext createEmptyContext() {
        return new FlowDataContext();
    }
}

