package com.heshuang.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.heshuang.core.base.dto.admin.CompanyPageReqDTO;
import com.heshuang.core.base.entity.admin.CompanyEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @description: 公司
 * @author: heshuang
 */
@Repository
public interface CompanyMapper extends BaseMapper<CompanyEntity> {
    /**
     * 分页列表查询
     *
     * @param page
     * @param reqDTO
     * @return
     */
    IPage<CompanyEntity> selectCompanyPageList(Page page, @Param("param") CompanyPageReqDTO reqDTO);

}
