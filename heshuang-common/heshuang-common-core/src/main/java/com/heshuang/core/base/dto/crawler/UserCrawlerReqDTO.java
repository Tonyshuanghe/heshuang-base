package com.heshuang.core.base.dto.crawler;

import lombok.Data;

import java.util.Date;

/**
 * @description:
 * @author: heshuang
 * @time: 2021/10/18 20:52
 */
@Data
public class UserCrawlerReqDTO {

    private String id;

    private String userName;

    private String nickName;
    
    private String password;

    private Long companyId;

    private Date createTime;

    private Date updateTime;
}
