package com.heshuang.businessstart.base.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author heshuang
 * @version v1.0
 * CreationTime: - 2022/4/15 14:08
 * Description: 基础bean
 */
@Data
public class BaseList implements Serializable {

    @ApiModelProperty("key")
    private String key;

    @ApiModelProperty("value")
    private String value;


}
