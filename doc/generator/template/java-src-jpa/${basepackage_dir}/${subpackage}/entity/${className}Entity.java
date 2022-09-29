<#include "/macro.include"/>
<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.${subpackage}.entity;

import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

<#include "/java_imports.include">
@Data
@Entity
@Table(name = "${table.sqlName}")
public class ${className}Entity implements Serializable {

	<#list table.compositeIdColumns as column>
    /**${column.hibernateValidatorExprssion}
     *${column.columnAlias}
	 */
    @Id
    @Column(name = "${column.sqlName}")
    private ${column.simpleJavaType} ${column.columnNameLower};
    </#list>

    <#list table.notPkColumns as column>
    /**${column.hibernateValidatorExprssion}
      *${column.columnAlias}
	  */
    @Column(name = "${column.sqlName}")
    private ${column.simpleJavaType} ${column.columnNameLower};

    </#list>

}

