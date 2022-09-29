/*
 *
 */
package com.heshuang.businessstart.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heshuang.businessstart.demo.entity.DemoEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author heshuang
 * @version v1.0
 * CreationTime: - 2022-04-15 14:47:15
 * Description:测试表
 */
public interface DemoMapper extends BaseMapper<DemoEntity> {

    /**
     * Description 查查寻
     * Author heshuang
     * Date 2022/4/18 11:19
     *
     * @param pageNo
     * @param PageSize
     * @return java.util.List<com.heshuang.businessstart.demo.vo.resp.DemoResp>
     */
    List<DemoEntity> findPage1(@Param("pageNo") Integer pageNo, @Param("PageSize") Integer PageSize);
}