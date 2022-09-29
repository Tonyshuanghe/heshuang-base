<#include "/java_copyright.include">
<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
package ${basepackage}.${subpackage}.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ${basepackage}.${subpackage}.entity.${className}Entity;

<#include "/java_imports.include">
@Repository
public interface ${className}Repository extends PagingAndSortingRepository<${className}Entity, Long>,
        JpaSpecificationExecutor<${className}Entity> {
}