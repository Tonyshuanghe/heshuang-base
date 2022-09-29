package com.heshuang.core.base.dto.admin;

import com.heshuang.core.base.dto.base.PageParam;
import lombok.Data;

/**
 * @description:
 * @author: heshuang
 * @time: 2021/10/3 17:47
 */
@Data
public class RolePageReqDTO extends PageParam {

    private String roleName;
}
