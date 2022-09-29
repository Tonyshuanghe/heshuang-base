package com.heshuang.core.base.dto.admin;

import com.heshuang.core.base.dto.base.PageParam;
import lombok.Data;

/**
 * @description:
 * @author: heshuang
 * @time: 2021/9/20 20:24
 */
@Data
public class CompanyPageReqDTO extends PageParam {

    private String companyName;
}
