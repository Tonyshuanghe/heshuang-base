package com.heshuang.core.base.dto.cms;

import lombok.Data;

/**
 * @description:
 * @author: heshuang
 * @time: 2021/11/13 18:06
 */
@Data
public class CategoryAddOrUpdateReqDTO {

    private Long categoryId;

    private String categoryName;

    private Long companyId;
}
