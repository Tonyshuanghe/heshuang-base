<#include "/java_copyright.include">
<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
package ${basepackage}.${subpackage}.service;

import org.springframework.data.domain.Page;
import ${basepackage}.${subpackage}.vo.req.${className}Req;
import ${basepackage}.${subpackage}.vo.resp.${className}Resp;

<#include "/java_imports.include">
public interface I${className}Service {

	//获取${table.tableAlias}分页数据
	Page<${className}Resp> find(int pageNo, int pageSize);

	//通过id获取一个${table.tableAlias}实例
	${className}Resp get(Long id);

	//${table.tableAlias}修改
	int update(${className}Req ${classNameLower});

	//${table.tableAlias}添加
	int insert(${className}Req ${classNameLower});

	//${table.tableAlias}删除
	int delete(${className}Req ${classNameLower});

	//${table.tableAlias}批量删除
	int delete(List<String> ids);

}
