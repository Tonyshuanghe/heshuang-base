package com.heshuang.websocket.consumer.handler;

import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson.JSON;
import com.heshuang.core.base.utils.CommonUtils;
import com.heshuang.websocket.controller.WebSocketController;
import com.heshuang.dataflow.core.IDataFilter;

import java.util.List;

/**
 * @author heshuang
 * @version v1.0
 * CreationTime: - 2022/8/22 17:21
 * Description: 消息转发处理器
 */
public class ForwardConsumerHandler implements IDataFilter {


    @Override
    public List<Object> filter(List<Object> list) {
        if (CommonUtils.isNotEmpty(list)) {
            for (Object o : list) {
                SpringUtil.getBean(WebSocketController.class).sendMsgAll(JSON.toJSONString(o));
            }
        }
        return list;
    }



}
