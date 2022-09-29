package com.heshuang.core.base.dto.cms;

import lombok.Data;

/**
 * @description:
 * @author: heshuang
 * @time: 2021/11/13 19:39
 */
@Data
public class TagChangeStatusReqDTO {

    private Long tagId;

    private Integer status;
}
