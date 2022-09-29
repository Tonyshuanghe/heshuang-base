//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.jpa.plugs;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.metamodel.SingularAttribute;
import org.hibernate.Session;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

public class BoreSimpleJpaRepository<T, ID> extends SimpleJpaRepository<T, ID> implements BoreJpaRepository<T, ID> {
    private static final String ENTITIES_NULL_MSG = "Entities must not be null!";
    private final JpaEntityInformation<T, ?> entityInformation;
    private final EntityManager em;

    public BoreSimpleJpaRepository(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityInformation = entityInformation;
        this.em = entityManager;
    }

    public BoreSimpleJpaRepository(Class<T> domainClass, EntityManager em) {
        this(JpaEntityInformationSupport.getEntityInformation(domainClass, em), em);
    }

    @Override
    @Transactional(
        rollbackFor = {RuntimeException.class}
    )
    public <S extends T> void saveBatch(Iterable<S> entities) {
        this.saveBatch(entities, 500);
    }

    @Override
    @Transactional(
        rollbackFor = {RuntimeException.class}
    )
    public <S extends T> void saveBatch(Iterable<S> entities, int batchSize) {
        Assert.notNull(entities, "Entities must not be null!");
        int i = 0;
        Iterator var4 = entities.iterator();

        while(var4.hasNext()) {
            S entity = (S) var4.next();
            this.em.persist(entity);
            ++i;
            if (i % batchSize == 0) {
                this.em.flush();
                this.em.clear();
            }
        }

    }

    @Override
    @Transactional(
        rollbackFor = {RuntimeException.class}
    )
    public <S extends T> void updateBatch(Iterable<S> entities) {
        this.updateBatch(entities, 500);
    }

    @Override
    @Transactional(
        rollbackFor = {RuntimeException.class}
    )
    public <S extends T> void updateBatch(Iterable<S> entities, int batchSize) {
        Assert.notNull(entities, "Entities must not be null!");
        int i = 0;
        Session session = (Session)this.em.unwrap(Session.class);
        Iterator var5 = entities.iterator();

        while(var5.hasNext()) {
            S entity = (S) var5.next();
            session.update(entity);
            ++i;
            if (i % batchSize == 0) {
                this.em.flush();
                this.em.clear();
            }
        }

    }

    @Transactional(
        rollbackFor = {RuntimeException.class}
    )
    @Override
    public <S extends T> S saveOrUpdateByNotNullProperties(S entity) {
        Assert.notNull(entity, "Entity must not be null.");
        ID id = (ID) this.entityInformation.getId(entity);
        if (id != null) {
            this.em.persist(entity);
            return entity;
        } else {
            Optional<T> entityOptional = super.findById(id);
            if (!entityOptional.isPresent()) {
                this.em.persist(entity);
                return entity;
            } else {
                T oldEntity = entityOptional.get();
                BeanUtils.copyProperties(entity, oldEntity, this.getNullProperties(entity));
                this.em.merge(oldEntity);
                return entity;
            }
        }
    }

    @Transactional(
        rollbackFor = {RuntimeException.class}
    )
    @Override
    public <S extends T> void saveOrUpdateAllByNotNullProperties(Iterable<S> entities) {
        Assert.notNull(entities, "Entities must not be null!");
        Iterator var2 = entities.iterator();

        while(var2.hasNext()) {
            S entity = (S) var2.next();
            this.saveOrUpdateByNotNullProperties(entity);
        }

    }

    @Transactional(
        rollbackFor = {RuntimeException.class}
    )
    @Override
    public void deleteByIds(Iterable<ID> ids) {
        Assert.notNull(ids, "The given ids must not be null!");
        Iterator var2 = ids.iterator();

        while(var2.hasNext()) {
            ID id = (ID) var2.next();
            super.deleteById(id);
        }

    }

    @Transactional(
        rollbackFor = {RuntimeException.class}
    )
    @Override
    public void deleteBatchByIds(Iterable<ID> ids) {
        this.deleteBatchByIds(ids, 500);
    }

    @Transactional(
        rollbackFor = {RuntimeException.class}
    )
    @Override
    public void deleteBatchByIds(Iterable<ID> ids, int batchSize) {
        Assert.notNull(ids, "The given ids must not be null!");
        Assert.isTrue(batchSize > 0, "The given batchSize must not be <= 0.");
        String entityName = this.entityInformation.getEntityName();
        SingularAttribute<? super T, ?> idAttribute = this.entityInformation.getIdAttribute();
        String idName = idAttribute == null ? "id" : idAttribute.getName();
        String sql = String.format("delete from %s where %s in :batch_ids", entityName, idName);
        int i = 0;
        List<ID> batchIds = new ArrayList();
        Iterator var9 = ids.iterator();

        while(var9.hasNext()) {
            ID id = (ID) var9.next();
            if (id != null) {
                batchIds.add(id);
                ++i;
                if (i % batchSize == 0 && !batchIds.isEmpty()) {
                    this.doBatchDelete(sql, batchIds);
                    batchIds.clear();
                }
            }
        }

        if (!batchIds.isEmpty()) {
            this.doBatchDelete(sql, batchIds);
        }

    }

    private void doBatchDelete(String sql, List<ID> batchIds) {
        this.em.createQuery(sql).setParameter("batch_ids", batchIds).executeUpdate();
    }

    private String[] getNullProperties(Object entity) {
        BeanWrapper beanWrapper = new BeanWrapperImpl(entity);
        PropertyDescriptor[] propertyDescriptors = beanWrapper.getPropertyDescriptors();
        List<String> nullProperties = new ArrayList();
        PropertyDescriptor[] var5 = propertyDescriptors;
        int var6 = propertyDescriptors.length;

        for(int var7 = 0; var7 < var6; ++var7) {
            PropertyDescriptor propertyDescriptor = var5[var7];
            String propertyName = propertyDescriptor.getName();
            Object propertyValue = beanWrapper.getPropertyValue(propertyName);
            if (propertyValue == null) {
                nullProperties.add(propertyName);
            }
        }

        return (String[])nullProperties.toArray(new String[0]);
    }
}
