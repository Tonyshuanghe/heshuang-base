<#include "/macro.include"/>
<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.${subpackage}.vo.req;

import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

<#include "/java_imports.include">
@Data
public class ${className}Req implements Serializable {

    <#list table.columns as column>
    //${column.hibernateValidatorExprssion}
    @ApiModelProperty(value = "${column.columnAlias}")
    private ${column.simpleJavaType} ${column.columnNameLower};

    </#list>

}

