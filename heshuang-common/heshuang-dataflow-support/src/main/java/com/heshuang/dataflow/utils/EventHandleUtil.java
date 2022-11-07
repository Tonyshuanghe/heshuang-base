//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.dataflow.utils;

import com.google.common.collect.Lists;
import com.heshuang.core.base.exception.BusinessException;
import com.heshuang.dataflow.core.DataFilterProxy;
import com.heshuang.dataflow.core.context.DataContextHolder;
import com.heshuang.dataflow.core.context.FlowDataContext;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class EventHandleUtil {
    public EventHandleUtil() {
    }

    public static List<Object> or(Collection<DataFilterProxy> needHandles) {
        int concurrent = needHandles.size() > 100 ? 100 : needHandles.size();
        FlowDataContext flowDataContext = DataContextHolder.getContext();
        List<Object> data = DataContextHolder.getContext().getData();
        List<Object> resultData = Lists.newArrayList();
        Flux.fromStream(needHandles.stream()).flatMap((item) -> {
            return Mono.fromCallable(() -> {
                DataContextHolder.setContext(flowDataContext);
                return item.filter(data);
            }).subscribeOn(Schedulers.elastic()).doFinally((f) -> {
                flowDataContext.copyRequestMap(DataContextHolder.getContext().requestMap());
                DataContextHolder.clear();
            });
        }, concurrent).doOnError((e) -> {
            throw BusinessException.of(e.getMessage(), e);
        }).toIterable().iterator().forEachRemaining((d) -> {
            resultData.addAll(d);
        });
        return resultData;
    }

    public static <T, S> List<T> and(Collection<S> needHandles, Supplier<? extends T> supplier) {
        return (List)Flux.fromStream(needHandles.stream()).flatMap((item) -> {
            return Mono.fromSupplier(supplier);
        }).doOnError((e) -> {
            throw BusinessException.of(e.getMessage(), e);
        }).toStream().collect(Collectors.toList());
    }
}
