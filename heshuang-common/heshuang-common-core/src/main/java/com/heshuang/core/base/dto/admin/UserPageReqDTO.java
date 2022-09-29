package com.heshuang.core.base.dto.admin;

import com.heshuang.core.base.dto.base.PageParam;
import lombok.Data;

/**
 * @description:
 * @author: heshuang
 * @time: 2021/10/3 14:10
 */
@Data
public class UserPageReqDTO extends PageParam {

    private String nickName;
}
