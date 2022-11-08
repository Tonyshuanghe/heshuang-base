package com.heshuang.businessstart.supports.security.config;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于配置白名单资源路径
 * Created by macro on 2018/11/5.
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "secure.ignored")
public class IgnoreUrlsConfig {

    public IgnoreUrlsConfig(){
        //默认放开路径
        urls = Lists.newArrayList(
                "/v3/api-docs",
                "/swagger-ui.html",
                "/swagger-resources/**",
                "/swagger/**",
                "/**/v2/api-docs",
                "/**/*.js",
                "/**/*.css",
                "/**/*.png",
                "/**/*.ico",
                "/webjars/springfox-swagger-ui/**",
                "/actuator/**",
                "/druid/**",
                "/*.html",
                "/auth/**",
                "/open/**",
                "/admin/**",
                "/demo/**",
                "/demoJpa/**",
                "/ws/**"
        );
    }

    private List<String> urls;

}
