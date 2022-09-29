<#include "/java_copyright.include">
<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
package ${basepackage}.${subpackage}.service.impl;

import com.sanrui.core.auth.UserUtils;
import com.sanrui.core.exceptions.BusinessException;
import com.sanrui.core.utils.BeansUtils;
import com.sanrui.core.utils.SequenceUtils;

import ${basepackage}.${subpackage}.dao.${className}Repository;
import ${basepackage}.${subpackage}.entity.${className}Entity;
import ${basepackage}.${subpackage}.vo.req.${className}Req;
import ${basepackage}.${subpackage}.vo.resp.${className}Resp;
import ${basepackage}.${subpackage}.service.I${className}Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

<#include "/java_imports.include">
@Service
public class ${className}ServiceImpl implements I${className}Service {

    @Resource
    private ${className}Repository ${classNameLower}Repository;

    @Override
    public Page<${className}Resp> find(int pageNo, int pageSize){
        return BeansUtils.copyProperties(${classNameLower}Repository.getPage(PageRequest.of(pageNo, pageSize)), ${className}Resp.class);
    }

    @Override
    public ${className}Resp get(Long id) {
        return BeansUtils.copyProperties(${classNameLower}Repository.getById(id), ${className}Resp.class);
    }

    @Override
    public int update(${className}Req ${classNameLower}){
        ${className}Entity ${classNameLower}Entity = BeansUtils.copyProperties(${classNameLower}, ${className}Entity.class);
        ${classNameLower}Entity.setUpdateTm(new Date());
        ${classNameLower}Entity.setUpdateBy(UserUtils.getUserId());
        ${classNameLower}Repository.saveAndFlush(${classNameLower}Entity);
        return 200;
    }

    @Override
    public int insert(${className}Req ${classNameLower}){
        ${className}Entity ${classNameLower}Entity = BeansUtils.copyProperties(${classNameLower}, ${className}Entity.class);
        ${classNameLower}Entity.setId(SequenceUtils.generate());
        ${classNameLower}Entity.setCreateTm(new Date());
        ${classNameLower}Entity.setCreateBy(UserUtils.getUserId());
        ${classNameLower}Entity.setIsFlag(1);
        ${classNameLower}Repository.saveAndFlush(${classNameLower}Entity);
        return 200;
    }

    @Override
    public int delete(${className}Req ${classNameLower}){
        ${className}Entity ${classNameLower}Entity = BeansUtils.copyProperties(${classNameLower}, ${className}Entity.class);
        ${classNameLower}Entity.setIsFlag(0);
        ${classNameLower}Entity.setUpdateTm(new Date());
        ${classNameLower}Entity.setUpdateBy(UserUtils.getUserId());
        ${classNameLower}Repository.saveAndFlush(${classNameLower}Entity);
        return 200;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(List<String> ids) {
        for (String id : ids) {
            // TODO
        }
        return 200;
    }

}
