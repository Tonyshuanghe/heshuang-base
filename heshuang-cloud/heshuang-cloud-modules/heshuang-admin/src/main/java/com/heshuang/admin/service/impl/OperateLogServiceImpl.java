package com.heshuang.admin.service.impl;

import com.heshuang.admin.service.OperateLogService;
import com.heshuang.core.base.dto.common.OperateLog;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: heshuang
 */
@Service
public class OperateLogServiceImpl implements OperateLogService {


    @Override
    public OperateLog add(OperateLog operLog) {
       return operLog;
    }
}
