package com.heshuang.knife4j.config;

import com.heshuang.knife4j.props.SwaggerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ParameterType;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author heshuang
 * @Description Swagger全局配置
 * @Date 17:22 2020-09-03
 * @Param
 * @return
 **/
@Component
@EnableSwagger2
@Import({SwaggerProperties.class,SwaggerResourceConfig.class})
@Order
public class SwaggerConfig {

    @Autowired
    private SwaggerProperties swaggerProperties;

    @Autowired
    private DefaultListableBeanFactory beanFactory;


    @PostConstruct
    public void autowireBean() {
        swaggerProperties.getService().stream().forEach(item -> {
                    Docket docket = docket(item.getName(), item.getBasePackage());
                    beanFactory.registerSingleton(String.valueOf(docket.hashCode()), docket);
                }
        );
    }

    private Docket docket(String serviceName, String basePackage) {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName(serviceName)
                //配置全局请求头
                .globalRequestParameters(headers())
                .select()
                //这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .paths(PathSelectors.any())
                .build();
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(swaggerProperties.getTitle())
                .description(swaggerProperties.getDescription())
                .version(swaggerProperties.getVersion())
                .build();
    }

    private List<RequestParameter> headers() {
        return Arrays.stream(swaggerProperties.getHeaders().split(","))
                .map(item ->
                        new RequestParameterBuilder().in(ParameterType.HEADER).name(item).build()
                ).collect(Collectors.toList());
    }
}
