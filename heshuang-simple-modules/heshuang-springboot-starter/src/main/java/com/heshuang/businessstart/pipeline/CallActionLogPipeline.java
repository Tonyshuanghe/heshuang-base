package com.heshuang.businessstart.pipeline;

import cn.hutool.core.thread.ThreadUtil;
import com.alibaba.fastjson.JSONObject;

import com.heshuang.logger.store.AbstractLoggerPipeline;
import com.heshuang.logger.store.ActionLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author heshuang
 * @version v1.0
 * CreationTime: - 2022/4/7 16:04
 * Description: 调用日志 ActionLog实现类
 */
@Component
@Slf4j
public class CallActionLogPipeline extends AbstractLoggerPipeline {

    @Autowired
    private RedisTemplate redisTemplate;

    private ThreadLocal<String> threadSysUser = ThreadUtil.createThreadLocal(false);


    public void setUserId(String userId) {
        threadSysUser.set(userId);
    }

    @Override
    protected void writeLogger(List<ActionLog> list) {
      //TODO 写入数据
        log.info(list.toString());
    }

    @Override
    public void synchronousResource(JSONObject properties) {
       //同步数据
        log.info(properties.toJSONString());
    }


}
