<#include "/java_copyright.include">
<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
package ${basepackage}.${subpackage}.service.impl;

import com.sanrui.core.auth.UserUtils;
import com.sanrui.core.exceptions.BusinessException;
import com.sanrui.core.utils.BeansUtils;
import com.sanrui.core.utils.SequenceUtils;

import ${basepackage}.${subpackage}.entity.${className}Entity;
import ${basepackage}.${subpackage}.vo.req.${className}Req;
import ${basepackage}.${subpackage}.vo.resp.${className}Resp;
import ${basepackage}.${subpackage}.service.I${className}Service;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;
import com.sanrui.inf.logger.anno.Model;
import com.sanrui.inf.logger.handler.OldValueLoader;
import com.sanrui.inf.logger.anno.OperateLogger;
import com.sanrui.inf.logger.anno.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ${basepackage}.${subpackage}.mapper.${className}Mapper;
import lombok.extern.slf4j.Slf4j;
import com.sanrui.core.constants.RestExStatus;



import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

<#include "/java_imports.include">
@Service
@Slf4j
@Model
public class ${className}ServiceImpl  extends ServiceImpl<${className}Mapper,${className}Entity>  implements I${className}Service, OldValueLoader  {


    @Override
    public Page<${className}Resp> find(int pageNo, int pageSize){
        IPage page = page(new com.baomidou.mybatisplus.extension.plugins.pagination.Page(pageNo+1, pageSize));
        page.setRecords(BeansUtils.copyProperties(page.getRecords(),${className}Resp.class));
        return new PageImpl(page.getRecords(), PageRequest.of(pageNo, pageSize), page.getTotal());    
	}

    @Override
    public ${className}Resp get(Long id) {
        return BeansUtils.copyProperties(getById(id), ${className}Resp.class);
    }

    @Override
    public int update(${className}Req ${classNameLower}){
        ${className}Entity ${classNameLower}Entity = BeansUtils.copyProperties(${classNameLower}, ${className}Entity.class);
        updateById(${classNameLower}Entity);
        return RestExStatus.SUCCESS.getValue();
    }

    @Override
    public int insert(${className}Req ${classNameLower}){
        ${className}Entity ${classNameLower}Entity = BeansUtils.copyProperties(${classNameLower}, ${className}Entity.class);
        save(${classNameLower}Entity);
        return RestExStatus.SUCCESS.getValue();
    }

    @Override
    public int delete(Long id){
        ${className}Entity ${classNameLower}Entity = getById(id);
        removeById(${classNameLower}Entity);
        return RestExStatus.SUCCESS.getValue();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(List<String> ids) {
        for (String id : ids) {
                 delete(Long.valueOf(id));
        }
        return RestExStatus.SUCCESS.getValue();
    }
	
	@Override
    public Object get(String s, Object o) throws Exception {
        return getById(Long.valueOf((String) o));
    }

}
