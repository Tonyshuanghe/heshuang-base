/*
 * 
 */

package com.heshuang.businessstart.demojpa.controller;

import com.heshuang.businessstart.demojpa.service.IDemoJpaService;
import com.heshuang.businessstart.demojpa.vo.req.DemoJpaReq;
import com.heshuang.businessstart.demojpa.vo.resp.DemoJpaResp;
import com.heshuang.core.base.result.RestResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;
import java.util.List;

/**
 * @author heshuang
 * @version v1.0
 * CreationTime: - 2022-08-18 09:53:17
 * Description:设备表
 */
@Api(tags = "设备表jpa")
@Slf4j
@RestController
@RequestMapping("/demoJpa")//模板无法自动生成，请按rest规范修改！！！
public class DemoJpaController {

    @Resource
    private IDemoJpaService demoJpaService;

    @GetMapping("/lists")
    @ApiOperation(value="获取分页设备表数据", nickname = "demoJpaList")
    public RestResult<Page<DemoJpaResp>> demoJpaList(@RequestParam(value = "pageNo") int pageNo,
                                                     @RequestParam(value = "pageSize") int pageSize){
        return RestResult.of("查询成功", demoJpaService.find(pageNo, pageSize));
    }

    @GetMapping("/{id}")
    @ApiOperation(value="获取设备表", nickname = "getDemoJpa")
    public RestResult<DemoJpaResp> getDemoJpa(@PathVariable("id") Long id) {
        return RestResult.of("查询成功", demoJpaService.get(id));
    }

    @PutMapping
    @ApiOperation(value="更新设备表", nickname = "updateDemoJpa")
    public RestResult<Integer> updateDemoJpa(@RequestBody DemoJpaReq demoJpaReq) {
        return RestResult.of("修改成功", demoJpaService.update(demoJpaReq));
    }

    @PostMapping
    @ApiOperation(value="新增设备表", nickname = "insertDemoJpa")
    public RestResult<Integer> insertDemoJpa(@Valid @RequestBody DemoJpaReq demoJpaReq) {
        return RestResult.of("录入成功", demoJpaService.insert(demoJpaReq));
    }

    @DeleteMapping
    @ApiOperation(value="删除设备表", nickname = "deleteDemoJpa")
    public RestResult<Integer> deleteDemoJpa(@Valid @RequestBody DemoJpaReq demoJpaReq) {
        return RestResult.of("删除成功", demoJpaService.delete(demoJpaReq));
    }

    @DeleteMapping("/batch")
    @ApiOperation(value = "批量删除设备表", nickname = "batchDelete")
    public RestResult<Integer> batchDelete(@Validated @Size(min = 1) @RequestBody List<String> ids) {
        return RestResult.of("删除成功", demoJpaService.delete(ids));
    }

}
