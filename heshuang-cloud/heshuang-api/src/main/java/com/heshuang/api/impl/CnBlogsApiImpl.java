package com.heshuang.api.impl;

import com.heshuang.api.CnBlogsApi;
import com.heshuang.core.base.result.RespBody;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public  class CnBlogsApiImpl implements CnBlogsApi, FallbackFactory<CnBlogsApi> {


    @Override
    public RespBody getToken() {
        return null;
    }

    @Override
    public RespBody getPersonalBlogInfo(String username) {
        return null;
    }

    @Override
    public RespBody getPersonalBlogPostList(String userName, Integer pageIndex) {
        return null;
    }

    @Override
    public RespBody getEssenceAreaPostList(String pageIndex, String pageSize) {
        return null;
    }

    @Override
    public RespBody getSiteHomePostList(String pageIndex, String pageSize) {
        return null;
    }

    @Override
    public CnBlogsApi create(Throwable throwable) {
        return new CnBlogsApiImpl();
    }
}