/*
 * 
 */
package com.heshuang.businessstart.demojpa.vo.req;

import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

/**
 * @author heshuang
 * @version v1.0
 * CreationTime: - 2022-08-18 09:53:17
 * Description:设备表
 */
@Data
public class DemoJpaReq implements Serializable {

    //
    @ApiModelProperty(value = "id")
    private Long id;

    //@NotNull 
    @ApiModelProperty(value = "1:正常|0:删除，默认值为1")
    private Boolean isFlag;

    //@NotNull 
    @ApiModelProperty(value = "创建时间")
    private Date createTm;

    //
    @ApiModelProperty(value = "更新时间")
    private Date updateTm;

    //
    @ApiModelProperty(value = "version")
    private Integer version;


}

