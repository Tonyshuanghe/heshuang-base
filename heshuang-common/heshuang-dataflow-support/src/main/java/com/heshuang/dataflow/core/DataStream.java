//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.dataflow.core;

import com.heshuang.core.base.exception.BusinessException;
import com.heshuang.dataflow.core.context.DataContextHolder;
import com.heshuang.dataflow.core.filters.EmptyDataFilter;
import com.heshuang.dataflow.core.filters.JdbcQueryFilter;
import com.heshuang.dataflow.core.filters.JdbcSaveFilter;
import com.heshuang.dataflow.core.filters.JdbcUpdateFilter;
import com.heshuang.dataflow.core.filters.RestFilter;
import com.heshuang.dataflow.core.sources.RabbitmqSource;

public class DataStream {
    private IDataSource dataSource;
    private DataFilterProxy rootFilter;
    private DataFilterProxy andFilter;
    private DataFilterProxy doFilter;

    public DataStream(IDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static DataStream build(IDataSource dataSource) {
        return new DataStream(dataSource);
    }

    public static DataStream rabbit(String topic, Class sourceType, Boolean failTry) {
        IDataSource dataSource = new RabbitmqSource(topic, sourceType, failTry);
        return build(dataSource);
    }

    public static DataStream delayed(Class sourceType, Boolean failTry) {
        IDataSource dataSource = new RabbitmqSource("dle.queue", sourceType, failTry);
        return build(dataSource);
    }

    public static DataStream delayed(Class sourceType) {
        return delayed(sourceType, false);
    }

    public static DataStream rabbit(String topic, Boolean failTry) {
        IDataSource dataSource = new RabbitmqSource(topic, (Class)null, true);
        return build(dataSource);
    }

    public static DataStream rabbit(String topic, Class sourceType) {
        IDataSource dataSource = new RabbitmqSource(topic, sourceType);
        return build(dataSource);
    }

    public static DataStream rabbit(String topic) {
        IDataSource dataSource = new RabbitmqSource(topic, (Class)null);
        return build(dataSource);
    }

    public <T> DataStream jdbcQuery(String sqlTpl, Class<T> clazz) {
        IDataFilter dataFilter = new JdbcQueryFilter(sqlTpl, clazz);
        this.todo(dataFilter);
        return this;
    }

    public DataStream rename(String name) {
        if (this.doFilter != null) {
            this.doFilter.setName(name);
        }

        return this;
    }

    public <T> DataStream jdbcSave(String saveKey, Class<T> queryClazz) {
        IDataFilter dataFilter = new JdbcSaveFilter(saveKey, queryClazz, (String)null);
        this.todo(dataFilter);
        return this;
    }

    public <T> DataStream jdbcSave(Class<T> queryClazz) {
        return this.jdbcSave((String)null, queryClazz);
    }

    public <T> DataStream jdbcExec(String saveKey, Class<T> queryClazz, String sql) {
        IDataFilter dataFilter = new JdbcSaveFilter(saveKey, queryClazz, sql);
        this.todo(dataFilter);
        return this;
    }

    public <T> DataStream jdbcExec(Class<T> queryClazz, String sql) {
        return this.jdbcExec((String)null, queryClazz, sql);
    }

    public <T> DataStream jdbcExec(Class<T> queryClazz) {
        return this.jdbcExec((String)null, queryClazz, (String)null);
    }

    public <T> DataStream jdbcUpdate(String saveKey, Class<T> queryClazz) {
        IDataFilter dataFilter = new JdbcUpdateFilter(saveKey, queryClazz);
        this.todo(dataFilter);
        return this;
    }

    public <T> DataStream jdbcUpdate(Class<T> queryClazz) {
        return this.jdbcUpdate((String)null, queryClazz);
    }

    public <T> DataStream rest(String method, String queryTpl, Class<T> queryClazz) {
        IDataFilter dataFilter = new RestFilter(method, queryTpl, queryClazz);
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

    public void handle() {
        this.dataSource.start();
        this.clear();
    }

    public void stop() {
        this.dataSource.stop();
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
