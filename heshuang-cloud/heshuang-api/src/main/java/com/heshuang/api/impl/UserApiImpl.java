package com.heshuang.api.impl;

import com.heshuang.api.CnBlogsApi;
import com.heshuang.api.UserApi;
import com.heshuang.core.base.dto.auth.UserIdReqDTO;
import com.heshuang.core.base.result.RespBody;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public  class UserApiImpl implements UserApi, FallbackFactory<UserApi> {



    @Override
    public UserApi create(Throwable throwable) {
        log.error("创建 hystrix.FallbackFactory.UserApiImpl");
        return new UserApiImpl();
    }

    @Override
    public RespBody<List<String>> getPerm(UserIdReqDTO reqDTO) {
        return RespBody.fail("请求权限列表出错");
    }

    @Override
    public RespBody<List<String>> getRole(UserIdReqDTO reqDTO) {
        return RespBody.fail("请求角色列表出错");
    }
}