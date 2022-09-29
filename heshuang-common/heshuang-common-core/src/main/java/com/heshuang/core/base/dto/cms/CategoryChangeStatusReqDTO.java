package com.heshuang.core.base.dto.cms;

import lombok.Data;

/**
 * @description:
 * @author: heshuang
 * @time: 2021/11/13 18:17
 */
@Data
public class CategoryChangeStatusReqDTO {

    private Long categoryId;

    private Integer status;
}
