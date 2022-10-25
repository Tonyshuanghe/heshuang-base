<#include "/java_copyright.include">
<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
package ${basepackage}.${subpackage}.controller;

import com.sanrui.core.models.RestResult;

import ${basepackage}.${subpackage}.vo.req.${className}Req;
import ${basepackage}.${subpackage}.vo.resp.${className}Resp;
import ${basepackage}.${subpackage}.service.I${className}Service;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;
import java.util.List;

<#include "/java_imports.include">
@Api(tags = "${table.tableAlias}")
@Slf4j
@RestController
@RequestMapping("/${classNameLower}")//模板无法自动生成，请按rest规范修改！！！
public class ${className}Controller {

    @Resource
    private I${className}Service ${classNameLower}Service;

    @GetMapping("/lists")
    @ApiOperation(value="获取分页${table.tableAlias}数据", nickname = "${classNameLower}List")
    public RestResult<Page<${className}Resp>> ${classNameLower}List(@RequestParam(value = "pageNo") int pageNo,
                                                                    @RequestParam(value = "pageSize") int pageSize){
        return RestResult.of("查询成功", ${classNameLower}Service.find(pageNo, pageSize));
    }

    @GetMapping("/{id}")
    @ApiOperation(value="获取${table.tableAlias}", nickname = "get${className}")
    public RestResult<${className}Resp> get${className}(@PathVariable("id") String id) {
        return RestResult.of("查询成功", ${classNameLower}Service.get(id));
    }

    @PutMapping
    @ApiOperation(value="更新${table.tableAlias}", nickname = "update${className}")
    public RestResult<Integer> update${className}(@RequestBody ${className}Req ${classNameLower}Req) {
        return RestResult.of("修改成功", ${classNameLower}Service.update(${classNameLower}Req));
    }

    @PostMapping
    @ApiOperation(value="新增${table.tableAlias}", nickname = "insert${className}")
    public RestResult<Integer> insert${className}(@Valid @RequestBody ${className}Req ${classNameLower}Req) {
        return RestResult.of("录入成功", ${classNameLower}Service.insert(${classNameLower}Req));
    }

    @DeleteMapping
    @ApiOperation(value="删除${table.tableAlias}", nickname = "delete${className}")
    public RestResult<Integer> delete${className}(@Valid @RequestBody ${className}Req ${classNameLower}Req) {
        return RestResult.of("删除成功", ${classNameLower}Service.delete(${classNameLower}Req));
    }

    @DeleteMapping("/batch")
    @ApiOperation(value = "批量删除${table.tableAlias}", nickname = "batchDelete")
    public RestResult<Integer> batchDelete(@Validated @Size(min = 1) @RequestBody List<String> ids) {
        return RestResult.of("删除成功", ${classNameLower}Service.delete(ids));
    }

}
