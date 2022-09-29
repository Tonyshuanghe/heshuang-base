<#include "/java_copyright.include">
<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
package ${basepackage}.${subpackage}.service;

import org.springframework.data.domain.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import ${basepackage}.${subpackage}.vo.req.${className}Req;
import ${basepackage}.${subpackage}.vo.resp.${className}Resp;
import ${basepackage}.${subpackage}.entity.${className}Entity;
import java.util.List;


<#include "/java_imports.include">
public interface I${className}Service  extends IService<${className}Entity> {

		/**
	 * Description 获取${table.tableAlias}分页数据
	 * @Author ${codeAuthor}
	 * @Date <#if now??>- ${now?string('yyyy-MM-dd HH:mm:ss')}</#if>
	 * @param pageNo
	 * @param pageSize
	 * @return com.baomidou.mybatisplus.extension.plugins.pagination.Page<${basepackage}.${subpackage}.vo.resp.${className}Resp>
	 */
	Page<${className}Resp> find(int pageNo, int pageSize);

		/**
	 * Description 通过id获取一个${table.tableAlias}实例
	 * @Author  ${codeAuthor}
	 * @Date <#if now??>- ${now?string('yyyy-MM-dd HH:mm:ss')}</#if>
	 * @param id
	 * @return ${basepackage}.${subpackage}.vo.resp.${className}Resp
	 */
	${className}Resp get(Long id);

	/**
	 * Description ${table.tableAlias}修改
	 * @Author ${codeAuthor}
	 * @Date <#if now??>- ${now?string('yyyy-MM-dd HH:mm:ss')}</#if>
	 * @param ${classNameLower}
	 * @return int
	 */
	int update(${className}Req ${classNameLower});

	/**
	 * Description ${table.tableAlias}添加
	 * @Author ${codeAuthor}
	 * @Date <#if now??>- ${now?string('yyyy-MM-dd HH:mm:ss')}</#if>
	 * @param ${classNameLower}
	 * @return int
	 */
	int insert(${className}Req ${classNameLower});

	/**
	 * Description ${table.tableAlias}删除
	 * @Author ${codeAuthor}
	 * @Date <#if now??>- ${now?string('yyyy-MM-dd HH:mm:ss')}</#if>
	 * @param id
	 * @return int
	 */
	int delete(Long id);

	/**
	 * Description ${table.tableAlias}批量删除
	 * @Author ${codeAuthor}
	 * @Date <#if now??>- ${now?string('yyyy-MM-dd HH:mm:ss')}</#if>
	 * @param ids
	 * @return int
	 */  
	int delete(List<String> ids);

}
