<#include "/macro.include"/>
<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.${subpackage}.entity;

import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.annotation.TableLogic;


<#include "/java_imports.include">
@Data
@TableName("${table.sqlName}")
@Accessors(chain = true)
public class ${className}Entity implements Serializable {

	<#list table.compositeIdColumns as column>
    /**含义: ${column.hibernateValidatorExprssion}
      *长度: ${column.columnAlias}
	  */
    @TableId("${column.sqlName}")
    private ${column.simpleJavaType} ${column.columnNameLower};
    </#list>

    <#list table.notPkColumns as column>
    /**含义: ${column.hibernateValidatorExprssion}
      *长度: ${column.columnAlias}
	  */
	<#if  column == "is_flag">
	@TableLogic(delval = "0",value = "1")
	</#if>
    @TableField("${column.sqlName}")
    private ${column.simpleJavaType} ${column.columnNameLower};
    </#list>
}

