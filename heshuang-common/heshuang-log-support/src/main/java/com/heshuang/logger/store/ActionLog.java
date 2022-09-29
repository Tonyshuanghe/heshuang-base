//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.logger.store;

import lombok.Builder;
import lombok.Data;

import java.util.Map;


@Data
@Builder
public class ActionLog {
    public static final String AREA_CODE = "areaCode";
    public static final String OPERATOR = "operator";
    public static final String OPERATOR_ID = "operatorId";
    public static final String OPERATOR_PROJECT_ID = "projectId";
    public static final String OPERATOR_PROJECT_NO = "projectNo";
    public static final String OPERATOR_IP = "operatorIP";
    public static final String BUSINESS_CODE = "bizCode";
    public static final String BUSINESS_ID = "bizId";
    public static final String OPERATE_TM = "operateTm";
    public static final String AUTHORITY = "authority";
    public static final String TENANT = "tenant";
    private Long id;
    private Long projectId;
    private String projectNo;
    private String operatorId;
    private String operator;
    private String operatorIP;
    private String bizCode;
    private String operateCode;
    private String bizId;
    private Long operateTm;
    private Integer contentType;
    private String content;
    private String tenant;
    private String authority;
    private Map<String, Object> extend;


}
