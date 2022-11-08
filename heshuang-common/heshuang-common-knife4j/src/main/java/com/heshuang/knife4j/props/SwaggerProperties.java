package com.heshuang.knife4j.props;

import com.google.common.collect.Lists;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author heshuang
 * @Description swagger 路由配置类
 * @Date 17:24 2020-09-03
 * @Param
 * @return
 **/
@Data
@Component
@Order
@ConfigurationProperties(prefix = "swagger")
public class SwaggerProperties {

    public SwaggerProperties(){
        //默认数据
        this.title = "Swagger接口文档";
        this.description = "Swagger接口文档";
        this.version = "3";
        this.contact = "272621263@qq.com";
        this.headers = "Authorization";
        this.service = Lists.newArrayList(new SwaggerService(){{setName("默认");setBasePackage("com.heshuang");}});
    }

    private String title;
    private String description;
    private String version;
    private String contact;
    private String headers;
    private List<SwaggerService> service;
    /**
     * yml内容
     * swagger:
     *   title: rabbitMqTest接口文档
     *   description: rabbitMqTest接口文档
     *   version: 3
     *   headers: Authorization,x-project-id,x-user-id,x-user-name
     *   service:
     *     - name: mq测试接口
     *       basePackage: com.heshuang.demo.mqdemo
     */
}
