package com.heshuang.core.base.dto.plugins.robot;

import lombok.Data;

/**
 * @description:
 * @author: heshuang
 */
@Data
public class RobotChatReqDTO {
    private Integer reqType;

    private PerceptionReqDTO perception;

    private UserInfoReqDTO userInfo;
}
