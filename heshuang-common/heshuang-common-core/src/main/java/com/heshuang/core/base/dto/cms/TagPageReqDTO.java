package com.heshuang.core.base.dto.cms;

import com.heshuang.core.base.dto.base.PageParam;
import lombok.Data;

/**
 * @description:
 * @author: heshuang
 * @time: 2021/11/13 19:35
 */
@Data
public class TagPageReqDTO extends PageParam {
    private String tagName;
}
