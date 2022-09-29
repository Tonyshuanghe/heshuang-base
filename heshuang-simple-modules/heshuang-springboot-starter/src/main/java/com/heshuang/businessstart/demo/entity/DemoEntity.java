/*
 *
 */
package com.heshuang.businessstart.demo.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.heshuang.businessstart.base.entity.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;


/**
 * @author heshuang
 * @version v1.0
 * CreationTime: - 2022-04-15 14:47:15
 * Description:设备表
 */
@Data
@TableName("demo")
@Accessors(chain = true)
public class DemoEntity extends BaseEntity implements Serializable {

    /**
     * 含义:
     * 长度: id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @Version
    private Integer version;
}

