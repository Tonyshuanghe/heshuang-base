/*
 * 
 */
package com.heshuang.businessstart.demojpa.vo.resp;

import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author heshuang
 * @version v1.0
 * CreationTime: - 2022-08-18 09:53:17
 * Description:设备表
 */
@Data
public class DemoJpaResp implements Serializable {

    //
	@JsonSerialize(using= ToStringSerializer.class)
    @ApiModelProperty(value = "id")
    private Long id;
    
    //@NotNull 
    @ApiModelProperty(value = "1:正常|0:删除，默认值为1")
    private Boolean isFlag;
    
    //@NotNull 
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间")
    private Date createTm;
    
    //
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "更新时间")
    private Date updateTm;
    
    //
    @ApiModelProperty(value = "version")
    private Integer version;
    

}

