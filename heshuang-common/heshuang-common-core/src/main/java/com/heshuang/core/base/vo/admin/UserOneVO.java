package com.heshuang.core.base.vo.admin;

import lombok.Data;

/**
 * @description:
 * @author: heshuang
 * @time: 2021/10/3 13:57
 */
@Data
public class UserOneVO {

    private String id;

    private String userName;

    private String email;

    private String phone;

    private String nickName;

    private Integer companyId;

    private Integer sex;

    private Integer status;
}
