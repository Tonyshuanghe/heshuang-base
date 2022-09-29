/*
 *
 */
package com.heshuang.businessstart.demo.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author heshuang
 * @version v1.0
 * CreationTime: - 2022-04-15 14:47:15
 * Description:设备表
 */
@Data
public class DemoReq implements Serializable {

    /**
     *
     */
    @ApiModelProperty(value = "id")
    private Long id;


}

