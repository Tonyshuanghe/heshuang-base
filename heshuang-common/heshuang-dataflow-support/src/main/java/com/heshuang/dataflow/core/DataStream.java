//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.dataflow.core;

import com.heshuang.core.base.exception.BusinessException;
import com.heshuang.dataflow.core.context.DataContextHolder;
import com.heshuang.dataflow.core.filters.EmptyDataFilter;
import com.heshuang.dataflow.core.filters.RestFilter;
import com.heshuang.dataflow.core.sources.MqttSource;
import com.heshuang.dataflow.core.sources.NoneSource;
import com.heshuang.dataflow.core.sources.RabbitmqSource;
import com.heshuang.dataflow.core.sources.SchedulerSource;

import java.util.List;

public class DataStream {
    private IDataSource dataSource;
    private DataFilterProxy rootFilter;
    private DataFilterProxy andFilter;
    private DataFilterProxy andStreamFilter;
    private DataFilterProxy doFilter;

    public DataStream(IDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static DataStream build(IDataSource dataSource) {
        return new DataStream(dataSource);
    }

    public static DataStream delayed(Class sourceType, Boolean failTry) {
        IDataSource dataSource = new RabbitmqSource(new String[]{"dle.queue"}, sourceType, failTry);
        return build(dataSource);
    }

    public static DataStream delayed(Class sourceType) {
        return delayed(sourceType, false);
    }

    public static DataStream rabbit(String topic, Class sourceType, Boolean failTry) {
        IDataSource dataSource = new RabbitmqSource(new String[]{topic}, sourceType, failTry);
        return build(dataSource);
    }

    public static DataStream cron(String cronStr, IDataFilter dataFilter) {
        IDataSource dataSource = new SchedulerSource(cronStr, dataFilter);
        return build(dataSource);
    }

    public static DataStream rabbit(String topic, Boolean failTry) {
        IDataSource dataSource = new RabbitmqSource(new String[]{topic}, (Class)null, true);
        return build(dataSource);
    }

    public static DataStream rabbit(String topic, Class sourceType) {
        IDataSource dataSource = new RabbitmqSource(new String[]{topic}, sourceType);
        return build(dataSource);
    }

    public static DataStream rabbit(String[] topic, Class sourceType, Boolean failTry) {
        IDataSource dataSource = new RabbitmqSource(topic, sourceType, failTry);
        return build(dataSource);
    }

    public static DataStream rabbit(String[] topic, Boolean failTry) {
        IDataSource dataSource = new RabbitmqSource(topic, (Class)null, true);
        return build(dataSource);
    }

    public static DataStream rabbit(String[] topic, Class sourceType) {
        IDataSource dataSource = new RabbitmqSource(topic, sourceType);
        return build(dataSource);
    }

    public static DataStream rabbit(String... topic) {
        IDataSource dataSource = new RabbitmqSource(topic, (Class)null);
        return build(dataSource);
    }

    public static DataStream none() {
        IDataSource dataSource = new NoneSource();
        return build(dataSource);
    }

    public static DataStream mqtt(String topic) {
        IDataSource dataSource = new MqttSource(topic, String.class, false);
        return build(dataSource);
    }

    public static DataStream mqtt(String topic, Class sourceType) {
        IDataSource dataSource = new MqttSource(topic, sourceType, false);
        return build(dataSource);
    }

    public static DataStream mqtt(String topic, Class sourceType, Boolean failTry) {
        IDataSource dataSource = new MqttSource(topic, sourceType, failTry);
        return build(dataSource);
    }

    public static DataStream mqtt(String topic, String routing, Class sourceType, Boolean failTry) {
        IDataSource dataSource = new MqttSource(topic, routing, sourceType, failTry);
        return build(dataSource);
    }

    public static DataStream mqtt(String topic, String routing, Class sourceType) {
        IDataSource dataSource = new MqttSource(topic, routing, sourceType, false);
        return build(dataSource);
    }

    public static DataStream mqtt(String topic, String routing) {
        IDataSource dataSource = new MqttSource(topic, routing, String.class, false);
        return build(dataSource);
    }



    public DataStream rename(String name) {
        if (this.doFilter != null) {
            this.doFilter.setName(name);
        }

        return this;
    }



    public <T> DataStream rest(String method, String queryTpl, Class<T> queryClazz) {
        IDataFilter dataFilter = new RestFilter(method, queryTpl, queryClazz);
        this.todo(dataFilter);
        return this;
    }

    public <T> DataStream rest(String method, String queryTpl) {
        IDataFilter dataFilter = new RestFilter(method, queryTpl, (Class)null);
        this.todo(dataFilter);
        return this;
    }

    public <T> DataStream rest(String queryTpl, Class<T> queryClazz) {
        IDataFilter dataFilter = new RestFilter("GET", queryTpl, queryClazz);
        this.todo(dataFilter);
        return this;
    }

    public DataStream todo(IDataFilter dataFilter) {
        if (this.rootFilter == null) {
            this.initRootFilter(dataFilter);
        } else if (this.doFilter.isSimple()) {
            this.doFilter = this.doFilter.next(new IDataFilter[]{dataFilter});
        } else {
            if (this.andFilter == null) {
                throw BusinessException.of("并行处理后必须设置and(IDataFilter)");
            }

            this.doFilter = this.andFilter.next(new IDataFilter[]{dataFilter});
        }

        return this;
    }

    private void initRootFilter(IDataFilter dataFilter) {
        if (this.rootFilter == null) {
            this.rootFilter = DataFilterProxy.wrap(dataFilter);
            this.doFilter = this.rootFilter;
            this.dataSource.setFilter(this.rootFilter);
        }

    }

    public DataStream.DataStreamParallel parallel(IDataFilter... dataFilters) {
        if (this.rootFilter == null) {
            this.initRootFilter(new EmptyDataFilter());
        }

        this.andFilter = this.doFilter.next(dataFilters);
        return new DataStream.DataStreamParallel(this, this.doFilter);
    }

    public DataStream.DataStreamParallel parallel(DataStream... dataStreams) {
        IDataFilter[] filters = new IDataFilter[dataStreams.length];

        for(int i = 0; i < dataStreams.length; ++i) {
            DataStream dataStream = dataStreams[i];
            filters[i] = (args) -> {
                return dataStream.handle(args);
            };
        }

        return this.parallel(filters);
    }

    public DataStream handle() {
        this.dataSource.start();
        this.clear();
        return this;
    }

    public List<Object> handle(List<Object> data) {
        this.dataSource.start();
        return this.dataSource.on(() -> {
            return data;
        });
    }

    public DataStream stop() {
        this.dataSource.stop();
        return this;
    }

    public void isActivate() {
        this.dataSource.isActivate();
    }

    public void clear() {
        DataContextHolder.clear();
        this.andFilter = null;
        this.doFilter = null;
    }

    public class DataStreamParallel {
        private DataStream dataStream;
        private DataFilterProxy doFilter;

        public DataStreamParallel(DataStream dataStream, DataFilterProxy doFilter) {
            this.dataStream = dataStream;
            this.doFilter = doFilter;
        }

        public DataStream and(IDataFilter dataFilter) {
            this.doFilter.and(dataFilter);
            return this.dataStream;
        }
    }
}
