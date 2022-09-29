/*
 *
 */
package com.heshuang.businessstart.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heshuang.businessstart.demo.entity.DemoEntity;
import com.heshuang.businessstart.demo.vo.req.DemoReq;
import com.heshuang.businessstart.demo.vo.resp.DemoResp;
import org.springframework.data.domain.Page;

import java.util.List;


/**
 * @author heshuang
 * @version v1.0
 * CreationTime: - 2022-04-15 14:47:15
 * Description:设备表
 */
public interface IDemoService extends IService<DemoEntity> {

    /**
     * Description 获取设备表分页数据
     *
     * @param pageNo
     * @param pageSize
     * @return com.baomidou.mybatisplus.extension.plugins.pagination.Page<com.heshuang.businessstart.demo.vo.resp.DemoResp>
     * @Author heshuang
     * @Date - 2022-04-15 14:47:15
     */
    Page<DemoResp> find(int pageNo, int pageSize);

    /**
     * Description 通过id获取一个设备表实例
     *
     * @param id
     * @return com.heshuang.businessstart.demo.vo.resp.DemoResp
     * @Author heshuang
     * @Date - 2022-04-15 14:47:15
     */
    DemoResp get(Long id);

    /**
     * Description 设备表修改
     *
     * @param demo
     * @return int
     * @Author heshuang
     * @Date - 2022-04-15 14:47:15
     */
    int update(DemoReq demo);

    /**
     * Description 设备表添加
     *
     * @param demo
     * @return int
     * @Author heshuang
     * @Date - 2022-04-15 14:47:15
     */
    int insert(DemoReq demo);

    /**
     * Description 设备表删除
     *
     * @param id
     * @return int
     * @Author heshuang
     * @Date - 2022-04-15 14:47:15
     */
    int delete(Long id);

    /**
     * Description 设备表批量删除
     *
     * @param ids
     * @return int
     * @Author heshuang
     * @Date - 2022-04-15 14:47:15
     */
    int delete(List<String> ids);

}
