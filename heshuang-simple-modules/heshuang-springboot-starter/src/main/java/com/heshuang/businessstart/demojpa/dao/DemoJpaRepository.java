/*
 * 
 */
package com.heshuang.businessstart.demojpa.dao;

import com.heshuang.businessstart.demojpa.entity.DemoJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author heshuang
 * @version v1.0
 * CreationTime: - 2022-08-18 09:53:17
 * Description:设备表
 */
@Repository
public interface DemoJpaRepository extends
        JpaSpecificationExecutor<DemoJpaEntity>, JpaRepository<DemoJpaEntity,Long> {
}