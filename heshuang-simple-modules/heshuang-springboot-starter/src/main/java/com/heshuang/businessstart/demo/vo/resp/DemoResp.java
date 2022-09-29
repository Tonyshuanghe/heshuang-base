/*
 *
 */
package com.heshuang.businessstart.demo.vo.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.heshuang.businessstart.base.conts.GenderType;
import com.heshuang.dict.anno.DictCode;
import com.heshuang.dict.anno.DictConf;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * @author heshuang
 * @version v1.0
 * CreationTime: - 2022-04-15 14:47:15
 * Description:设备表
 */
@Data
@DictConf
public class DemoResp implements Serializable {

    /**
     *
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "id")
    private Long id;


    /**
     * @NotNull
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTm;

    /**
     *
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "删除时间")
    private LocalDateTime deleteTm;

    /**
     *
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "删除时间")
    @DictCode(conts = GenderType.class)
    private String aaa;
}

