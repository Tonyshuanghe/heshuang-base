//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.dataflow.core.dao;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

public interface IBaseDao {
    @Transactional
    void save(Object var1);

    @Transactional
    int save(List<Object> var1);

    @Transactional
    void update(Object var1);

    @Transactional
    int update(List<Object> var1);

    @Transactional
    int[] updateSql(String var1, List<Object> var2);

    @Transactional
    <T> void delete(Class<T> var1, Object var2);

    @Transactional
    <T> void delete(Class<T> var1, Object[] var2);

    Long nativeQueryCount(String var1, Object... var2);

    <T> List<T> nativeQueryPagingList(Class<T> var1, Pageable var2, String var3, Object... var4);

    <T> List<T> nativeQueryList(Class<T> var1, String var2, Object... var3);
}
