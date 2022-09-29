package com.heshuang.core.base.dto.file;

import com.heshuang.core.base.dto.base.PageParam;
import lombok.Data;

/**
 * @description:
 * @author: heshuang
 * @time: 2021/9/28 20:31
 */
@Data
public class FilePageReqDTO extends PageParam {
    private String fileName;
}
