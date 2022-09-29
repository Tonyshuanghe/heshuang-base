package com.heshuang.api;

import com.heshuang.api.impl.OperateLogImpl;
import com.heshuang.core.base.constant.ApplicationConst;
import com.heshuang.core.base.dto.common.OperateLog;
import com.heshuang.core.base.result.RespBody;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @description:
 * @author: heshuang
 */
@FeignClient(contextId = "operateLogApi", name = ApplicationConst.ADMIN,fallbackFactory = OperateLogImpl.class)
public interface OperateLogApi {
    @PostMapping("/operate_log/add")
    RespBody add(@RequestBody OperateLog operateLog);
}
