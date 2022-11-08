package com.heshuang.knife4j.config;

import com.heshuang.knife4j.props.SwaggerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @description Swagger资源配置
 * @author heshuang
 * @date 2022/10/24 21:56
 * @Param
 * @param
 * @return {@link }
 **/
@Component
@Primary
@Import({SwaggerProperties.class})
@Order
public class SwaggerResourceConfig implements SwaggerResourcesProvider {

    private static final String API_URI = "/v3/api-docs?group=";

    @Autowired
    private SwaggerProperties swaggerProperties;

    @Override
    public List<SwaggerResource> get() {
        return swaggerProperties.getService().stream().map(item -> {
            String url = API_URI + item.getName();
            SwaggerResource swaggerResource = new SwaggerResource();
            swaggerResource.setName(item.getName());
            swaggerResource.setLocation(url);
            swaggerResource.setSwaggerVersion(swaggerProperties.getVersion());
            swaggerResource.setUrl(url);
            return swaggerResource;
        }).collect(Collectors.toList());
    }

}
