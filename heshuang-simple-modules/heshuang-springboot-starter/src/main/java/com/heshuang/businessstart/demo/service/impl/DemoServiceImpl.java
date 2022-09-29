/*
 *
 */
package com.heshuang.businessstart.demo.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heshuang.businessstart.demo.entity.DemoEntity;
import com.heshuang.businessstart.demo.mapper.DemoMapper;
import com.heshuang.businessstart.demo.service.IDemoService;
import com.heshuang.businessstart.demo.vo.req.DemoReq;
import com.heshuang.businessstart.demo.vo.resp.DemoResp;
import com.heshuang.businessstart.pipeline.CallActionLogPipeline;
import com.heshuang.core.base.constant.RestExStatus;
import com.heshuang.dict.DictUtils;
import com.heshuang.logger.anno.Model;
import com.heshuang.logger.anno.OperateLogger;
import com.heshuang.logger.handler.OldValueLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author heshuang
 * @version v1.0
 * CreationTime: - 2022-04-15 14:47:15
 * Description:测试
 */
@Service
@Slf4j
@Model(pipelineClass = CallActionLogPipeline.class)
public class DemoServiceImpl extends ServiceImpl<DemoMapper, DemoEntity> implements IDemoService, OldValueLoader {

    @Override
    @Cacheable(key = "'treeDeviceInfo'", cacheNames = "treeDeviceInfo")
    public Page<DemoResp> find(int pageNo, int pageSize) {
        IPage page = page(new com.baomidou.mybatisplus.extension.plugins.pagination.Page(pageNo + 1, pageSize));
        page.setRecords(BeanUtil.copyToList(page.getRecords(), DemoResp.class));
        for (Object record : page.getRecords()) {
            ((DemoResp)record).setAaa("1");
        }
        DictUtils.decode(page.getRecords());
        return new PageImpl(page.getRecords(), PageRequest.of(pageNo, pageSize), page.getTotal());
    }

    @Override
    public DemoResp get(Long id) {
        return BeanUtil.copyProperties(getById(id), DemoResp.class);
    }

    @Override
    public int update(DemoReq demo) {
        DemoEntity byId = getById(demo.getId());
        DemoEntity demoEntity = BeanUtil.copyProperties(demo, DemoEntity.class);
        demoEntity.setVersion(byId.getVersion());
        baseMapper.update(demoEntity, Wrappers.<DemoEntity>lambdaUpdate()
                .eq(DemoEntity::getId,demoEntity.getId()));
        return RestExStatus.SUCCESS.getValue();
    }

    @Override
    @OperateLogger(value = "#demo.id",operateCode = "CallActionLog")
    public int insert(DemoReq demo) {
        DemoEntity demoEntity = BeanUtil.copyProperties(demo, DemoEntity.class);
        demoEntity.setId(null);
        save(demoEntity);
        return RestExStatus.SUCCESS.getValue();
    }

    @Override
    public int delete(Long id) {
        DemoEntity demoEntity = getById(id);
        if (demoEntity != null) {
            removeById(demoEntity);
        }
        return RestExStatus.SUCCESS.getValue();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(key = "'treeDeviceInfo'", cacheNames = "treeDeviceInfo", beforeInvocation = true)
    public int delete(List<String> ids) {
        for (String id : ids) {
            delete(Long.valueOf(id));
        }
        return RestExStatus.SUCCESS.getValue();
    }

    @Override
    public Object get(String s, Object o) throws Exception {
        return getById(Long.valueOf((String) o));
    }
}
