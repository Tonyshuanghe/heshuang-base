package com.heshuang.core.base.dto.admin;

import lombok.Data;

/**
 * @description:
 * @author: heshuang
 * @time: 2021/10/3 17:04
 */
@Data
public class UserOldPwdValidReqDTO {

    private String userId;

    private String oldPwd;
}
