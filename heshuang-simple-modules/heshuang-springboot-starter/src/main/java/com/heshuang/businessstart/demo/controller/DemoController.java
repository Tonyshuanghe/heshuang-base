/*
 *
 */
package com.heshuang.businessstart.demo.controller;

import ch.qos.logback.core.joran.spi.XMLUtil;
import cn.hutool.core.util.XmlUtil;
import com.heshuang.businessstart.demo.service.IDemoService;
import com.heshuang.businessstart.demo.vo.req.DemoReq;
import com.heshuang.businessstart.demo.vo.resp.DemoResp;

import com.heshuang.core.base.result.RestResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;


/**
 * @author heshuang
 * @version v1.0
 * CreationTime: - 2022-04-15 14:47:15
 * Description:设备表
 */
@Api(tags = "设备表")
@RestController
@RequestMapping("/demo")
public class DemoController {

    @Resource
    private IDemoService demoService;

    @GetMapping("/lists")
    @ApiOperation(value = "获取分页设备表数据", nickname = "demoList")
    public RestResult<Page<DemoResp>> demoList(@RequestParam(value = "pageNo") int pageNo,
                                               @RequestParam(value = "pageSize") int pageSize) {
        return RestResult.of("查询成功", demoService.find(pageNo, pageSize));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "获取设备表", nickname = "getDemo")
    public RestResult<DemoResp> getDemo(@PathVariable("id") Long id) {
        return RestResult.of("查询成功", demoService.get(id));
    }

    @PutMapping
    @ApiOperation(value = "更新设备表", nickname = "updateDemo")
    public RestResult<Integer> updateDemo(@RequestBody DemoReq demoReq) {
        return RestResult.of("修改成功", demoService.update(demoReq));
    }

    @PostMapping
    @ApiOperation(value = "新增设备表", nickname = "insertDemo")
    public RestResult<Integer> insertDemo(@Valid @RequestBody DemoReq demoReq) {
        return RestResult.of("录入成功", demoService.insert(demoReq));
    }


    @DeleteMapping("/batch")
    @ApiOperation(value = "批量删除设备表", nickname = "batchDelete")
    public RestResult<Integer> batchDelete(@RequestBody List<String> ids) {
        return RestResult.of("删除成功", demoService.delete(ids));
    }

    @PostMapping("/notify")
    @ApiOperation(value = "notify", nickname = "notify")
    public RestResult<Integer> notify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 读取参数
        InputStream inputStream = request.getInputStream();
        StringBuffer sb = new StringBuffer();
        String s;
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        while ((s = in.readLine()) != null) {
            sb.append(s);
        }
        in.close();
        inputStream.close();
        Object o = XmlUtil.readObjectFromXml(sb.toString());
        System.out.println(o);
        return RestResult.of("录入成功", 200);
    }

}
