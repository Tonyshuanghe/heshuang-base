package com.heshuang.admin.service;


import com.heshuang.core.base.dto.common.OperateLog;

/**
 * @description:
 * @author: heshuang
 */
public interface OperateLogService {
    /**
     * 操作日志添加
     *
     * @param operLog
     * @return
     */
    OperateLog add(OperateLog operLog);
}
