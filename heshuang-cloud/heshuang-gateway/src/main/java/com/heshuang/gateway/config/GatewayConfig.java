package com.heshuang.gateway.config;

import com.alibaba.csp.sentinel.adapter.gateway.sc.SentinelGatewayFilter;
import com.heshuang.gateway.handle.SentinelFallbackHandler;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * 网关限流配置
 *
 * @author heshuang
 */
@Configuration
public class GatewayConfig
{
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SentinelFallbackHandler sentinelGatewayExceptionHandler()
    {
        return new SentinelFallbackHandler();
    }

    @Bean
    @Order(-1)
    public GlobalFilter sentinelGatewayFilter()
    {
        return new SentinelGatewayFilter();
    }
}