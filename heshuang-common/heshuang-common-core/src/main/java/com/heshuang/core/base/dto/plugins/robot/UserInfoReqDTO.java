package com.heshuang.core.base.dto.plugins.robot;

import lombok.Data;

/**
 * @description:
 * @author: heshuang
 */
@Data
public class UserInfoReqDTO {
    private String apiKey;
    private String userId = "robot";
}
