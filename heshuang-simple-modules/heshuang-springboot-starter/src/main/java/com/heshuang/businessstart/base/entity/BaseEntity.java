package com.heshuang.businessstart.base.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author heshuang
 * @version v1.0
 * CreationTime: - 2022/4/15 14:08
 * Description: 基础bean
 */
@Getter
@Setter
public class BaseEntity implements Serializable {

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTm;

    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTm;

    @TableField(select = false, fill = FieldFill.UPDATE)
    @ApiModelProperty(value = "逻辑删除1正常0删除")
    private Integer isFlag;

}
