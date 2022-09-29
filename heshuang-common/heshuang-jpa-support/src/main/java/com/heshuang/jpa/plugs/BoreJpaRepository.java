//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.jpa.plugs;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BoreJpaRepository<T, ID> extends JpaRepository<T, ID> {
    <S extends T> void saveBatch(Iterable<S> var1);

    <S extends T> void saveBatch(Iterable<S> var1, int var2);

    <S extends T> void updateBatch(Iterable<S> var1);

    <S extends T> void updateBatch(Iterable<S> var1, int var2);

    <S extends T> S saveOrUpdateByNotNullProperties(S var1);

    <S extends T> void saveOrUpdateAllByNotNullProperties(Iterable<S> var1);

    void deleteByIds(Iterable<ID> var1);

    void deleteBatchByIds(Iterable<ID> var1);

    void deleteBatchByIds(Iterable<ID> var1, int var2);
}
