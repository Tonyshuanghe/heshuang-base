/*
 * 
 */
package com.heshuang.businessstart.demojpa.service;

import com.heshuang.businessstart.demojpa.vo.req.DemoJpaReq;
import com.heshuang.businessstart.demojpa.vo.resp.DemoJpaResp;
import org.springframework.data.domain.Page;


import java.util.List;

/**
 * @author heshuang
 * @version v1.0
 * CreationTime: - 2022-08-18 09:53:17
 * Description:设备表
 */
public interface IDemoJpaService {

	//获取设备表分页数据
	Page<DemoJpaResp> find(int pageNo, int pageSize);

	//通过id获取一个设备表实例
	DemoJpaResp get(Long id);

	//设备表修改
	int update(DemoJpaReq demoJpa);

	//设备表添加
	int insert(DemoJpaReq demoJpa);

	//设备表删除
	int delete(DemoJpaReq demoJpa);

	//设备表批量删除
	int delete(List<String> ids);

}
