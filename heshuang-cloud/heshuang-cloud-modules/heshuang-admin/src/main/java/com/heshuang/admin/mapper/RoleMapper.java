package com.heshuang.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.heshuang.core.base.dto.admin.RolePageReqDTO;
import com.heshuang.core.base.entity.admin.RoleEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @description: 角色
 * @author: heshuang
 */
@Repository
public interface RoleMapper extends BaseMapper<RoleEntity> {

    /**
     * 分页列表查询
     *
     * @param page
     * @param reqDTO
     * @return
     */
    IPage<RoleEntity> selectRolePageList(Page page, @Param("param") RolePageReqDTO reqDTO);

}
