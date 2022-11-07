//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.dataflow.core.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.transaction.annotation.Transactional;

public class EntityBaseDao implements IBaseDao {
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public EntityBaseDao() {
    }

    public EntityManager getEntityManager() {
        return this.em;
    }
    @Override

    public void save(Object entity) {
        this.em.persist(entity);
    }
    @Override

    public int save(List<Object> entity) {
        int saveCount = 0;

        for(int i = 0; i < entity.size(); ++i) {
            this.em.persist(entity.get(i));
            ++saveCount;
        }

        this.em.flush();
        return saveCount;
    }
    @Override

    public void update(Object entity) {
        this.em.merge(entity);
        this.em.refresh(entity);
    }
    @Override

    public int update(List<Object> entity) {
        int saveCount = 0;

        for(int i = 0; i < entity.size(); ++i) {
            this.em.persist(this.em.merge(entity.get(i)));
            ++saveCount;
        }

        this.em.flush();
        return saveCount;
    }

    @Override
    public int[] updateSql(String sql, List<Object> entity) {
        SqlParameterSource[] sqlParameterSource = SqlParameterSourceUtils.createBatch(entity.toArray());
        return this.jdbcTemplate.batchUpdate(sql, sqlParameterSource);
    }
    @Override

    public <T> void delete(Class<T> entityClass, Object entityid) {
        this.delete(entityClass, new Object[]{entityid});
    }

    @Override
    public <T> void delete(Class<T> entityClass, Object[] entityids) {
        Object[] var3 = entityids;
        int var4 = entityids.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            Object id = var3[var5];
            this.em.remove(this.em.getReference(entityClass, id));
        }

        this.em.flush();
    }

    @Transactional(
        readOnly = false
    )
    @Override

    public Long nativeQueryCount(String nativeSql, Object... params) {
        Object count = this.createNativeQuery(nativeSql, params).getSingleResult();
        return ((Number)count).longValue();
    }

    @Transactional(
        readOnly = false
    )    @Override

    public <T> List<T> nativeQueryPagingList(Class<T> resultClass, Pageable pageable, String nativeSql, Object... params) {
        Integer pageNumber = pageable.getPageNumber();
        Integer pageSize = pageable.getPageSize();
        Integer startPosition = pageNumber * pageSize;
        return this.createNativeQuery(resultClass, nativeSql, params).setFirstResult(startPosition).setMaxResults(pageSize).getResultList();
    }

    @Transactional(
        readOnly = false
    )    @Override

    public <T> List<T> nativeQueryList(Class<T> resultClass, String nativeSql, Object... params) {
        return this.createNativeQuery(resultClass, nativeSql, params).getResultList();
    }

    private Query createNativeQuery(String sql, Object... params) {
        Query q = this.em.createNativeQuery(sql);
        if (params != null && params.length > 0) {
            for(int i = 0; i < params.length; ++i) {
                q.setParameter(i + 1, params[i]);
            }
        }

        return q;
    }

    private <T> Query createNativeQuery(Class<T> resultClass, String sql, Object... params) {
        Query q = this.em.createNativeQuery(sql, resultClass);
        ((NativeQueryImpl)q.unwrap(NativeQueryImpl.class)).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        if (params != null) {
            for(int i = 0; i < params.length; ++i) {
                q.setParameter(i + 1, params[i]);
            }
        }

        return q;
    }
}
