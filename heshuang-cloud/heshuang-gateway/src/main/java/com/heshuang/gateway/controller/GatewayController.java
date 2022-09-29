package com.heshuang.gateway.controller;

import com.heshuang.core.base.result.RespBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: heshuang
 * @time: 2022/1/9 17:02
 */
@RestController
public class GatewayController {
    @PostMapping("/")
    public RespBody index() {
        return RespBody.success();
    }
}
