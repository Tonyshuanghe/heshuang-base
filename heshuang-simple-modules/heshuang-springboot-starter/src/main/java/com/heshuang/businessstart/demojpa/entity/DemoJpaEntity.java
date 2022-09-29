/*
 * 
 */
package com.heshuang.businessstart.demojpa.entity;

import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author heshuang
 * @version v1.0
 * CreationTime: - 2022-08-18 09:53:17
 * Description:设备表
 */
@Data
@Entity
@Table(name = "demo_jpa")
public class DemoJpaEntity implements Serializable {

    /**
     *id
	 */
    @Id
    @Column(name = "id")
    private Long id;

    /**@NotNull 
      *1:正常|0:删除，默认值为1
	  */
    @Column(name = "is_flag")
    private Integer isFlag;

    /**@NotNull 
      *创建时间
	  */
    @Column(name = "create_tm")
    private Date createTm;

    /**
      *更新时间
	  */
    @Column(name = "update_tm")
    private Date updateTm;

    /**
      *version
	  */
    @Column(name = "version")
    private Integer version;


}

