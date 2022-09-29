package com.heshuang.api.impl;

import com.heshuang.api.CnBlogsApi;
import com.heshuang.api.CrawlerApi;
import com.heshuang.core.base.result.RespBody;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public  class CrawlerApiImpl implements CrawlerApi, FallbackFactory<CrawlerApi> {



    @Override
    public CrawlerApi create(Throwable throwable) {
        return new CrawlerApiImpl();
    }

    @Override
    public RespBody cnblog_user() {
        return null;
    }

    @Override
    public RespBody cnblogs_home() {
        return null;
    }

    @Override
    public RespBody cnblog_es() {
        return null;
    }
}