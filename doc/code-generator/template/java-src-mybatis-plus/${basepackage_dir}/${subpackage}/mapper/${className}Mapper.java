<#include "/java_copyright.include">
<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
package ${basepackage}.${subpackage}.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ${basepackage}.${subpackage}.entity.${className}Entity;

<#include "/java_imports.include">
public interface ${className}Mapper extends BaseMapper<${className}Entity> {
}