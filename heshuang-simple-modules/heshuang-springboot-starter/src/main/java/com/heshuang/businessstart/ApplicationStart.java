package com.heshuang.businessstart;/**
 * @author heshuang
 * @version v1.0
 * CreationTime: - 2022/7/28 11:06
 * Description: TODO
 */

import com.heshuang.jpa.plugs.EnableBoreJpa;
import com.heshuang.logger.anno.EnableOperateLogger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableOperateLogger
@EnableBoreJpa
public class ApplicationStart {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationStart.class, args);
    }


}
