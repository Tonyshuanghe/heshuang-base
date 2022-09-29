package com.heshuang.core.base.dto.admin;

import lombok.Data;

/**
 * @description:
 * @author: heshuang
 * @time: 2021/10/3 16:57
 */
@Data
public class UserChangePwdReqDTO {
    private String userId;
    private String newPwd;
}
