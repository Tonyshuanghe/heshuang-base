package com.heshuang;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author heshuang
 * @version v1.0
 * CreationTime: - 2022/9/28 10:06
 * Description: 扫描类
 */
@Configuration
@ComponentScan("com.heshuang.api")
public class FeignFallbackSupports {
}
