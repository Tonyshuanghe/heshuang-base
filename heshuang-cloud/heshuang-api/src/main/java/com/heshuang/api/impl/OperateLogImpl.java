package com.heshuang.api.impl;

import com.heshuang.api.CnBlogsApi;
import com.heshuang.api.CrawlerApi;
import com.heshuang.api.OperateLogApi;
import com.heshuang.core.base.dto.common.OperateLog;
import com.heshuang.core.base.result.RespBody;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public  class OperateLogImpl implements OperateLogApi, FallbackFactory<OperateLogApi> {


    @Override
    public OperateLogApi create(Throwable throwable) {
        log.error("创建 hystrix.FallbackFactory.OperateLogImpl");
        return new OperateLogImpl();
    }

    @Override
    public RespBody add(OperateLog operateLog) {
        return RespBody.fail("新增日志调用 服务出错");
    }
}