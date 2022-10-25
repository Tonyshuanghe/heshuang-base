<#include "/macro.include"/>
<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.${subpackage}.vo.resp;

import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sanrui.core.serializer.IdToStringSerialize;

<#include "/java_imports.include">
@Data
public class ${className}Resp implements Serializable {

    <#list table.columns as column>
    //${column.hibernateValidatorExprssion}
    <#if column.javaType == 'Long'>
	@JsonSerialize(using= ToStringSerializer.class)
	<#elseif column.javaType == 'Date'>
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    <#else>
    </#if>
    @ApiModelProperty(value = "${column.columnAlias}")
    private ${column.simpleJavaType} ${column.columnNameLower};
    
    </#list>

}

