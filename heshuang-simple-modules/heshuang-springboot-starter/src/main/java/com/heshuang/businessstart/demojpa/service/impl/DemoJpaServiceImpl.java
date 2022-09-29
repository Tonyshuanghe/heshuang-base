/*
 * 
 */
package com.heshuang.businessstart.demojpa.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.heshuang.businessstart.demojpa.dao.DemoJpaRepository;
import com.heshuang.businessstart.demojpa.entity.DemoJpaEntity;
import com.heshuang.businessstart.demojpa.service.IDemoJpaService;
import com.heshuang.businessstart.demojpa.vo.req.DemoJpaReq;
import com.heshuang.businessstart.demojpa.vo.resp.DemoJpaResp;


import com.heshuang.core.base.utils.SnowflakeUtils;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author heshuang
 * @version v1.0
 * CreationTime: - 2022-08-18 09:53:17
 * Description:设备表
 */
@Service
public class DemoJpaServiceImpl implements IDemoJpaService {

    @Resource
    private DemoJpaRepository demoJpaRepository;

    @Override
    public Page<DemoJpaResp> find(int pageNo, int pageSize){
        Page page =  demoJpaRepository.findAll(PageRequest.of(pageNo, pageSize));
        return new PageImpl<DemoJpaResp>(BeanUtil.copyToList(page.getContent(), DemoJpaResp.class),PageRequest.of(pageNo, pageSize),page.getTotalElements());
    }

    @Override
    public DemoJpaResp get(Long id) {
        return BeanUtil.copyProperties(demoJpaRepository.getOne(id), DemoJpaResp.class);
    }

    @Override
    public int update(DemoJpaReq demoJpa){
        DemoJpaEntity demoJpaEntity = BeanUtil.copyProperties(demoJpa, DemoJpaEntity.class);
        demoJpaEntity.setUpdateTm(new Date());
        demoJpaRepository.saveAndFlush(demoJpaEntity);
        return 200;
    }

    @Override
    public int insert(DemoJpaReq demoJpa){
        DemoJpaEntity demoJpaEntity = BeanUtil.copyProperties(demoJpa, DemoJpaEntity.class);
        demoJpaEntity.setId(SnowflakeUtils.generate());
        demoJpaEntity.setCreateTm(new Date());
        demoJpaEntity.setIsFlag(1);
        demoJpaRepository.saveAndFlush(demoJpaEntity);
        return 200;
    }

    @Override
    public int delete(DemoJpaReq demoJpa){
        DemoJpaEntity demoJpaEntity = BeanUtil.copyProperties(demoJpa, DemoJpaEntity.class);
        demoJpaEntity.setIsFlag(0);
        demoJpaEntity.setUpdateTm(new Date());
        demoJpaRepository.saveAndFlush(demoJpaEntity);
        return 200;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(List<String> ids) {
        for (String id : ids) {
            // TODO
        }
        return 200;
    }

}
