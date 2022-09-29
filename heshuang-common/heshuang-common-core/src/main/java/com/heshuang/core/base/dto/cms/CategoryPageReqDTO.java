package com.heshuang.core.base.dto.cms;

import com.heshuang.core.base.dto.base.PageParam;
import lombok.Data;

/**
 * @description:
 * @author: heshuang
 * @time: 2021/11/13 17:58
 */
@Data
public class CategoryPageReqDTO extends PageParam {
    private String categoryName;
}
