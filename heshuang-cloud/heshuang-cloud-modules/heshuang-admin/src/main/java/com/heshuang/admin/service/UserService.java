package com.heshuang.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.heshuang.core.base.dto.admin.UserAddOrUpdateReqDTO;
import com.heshuang.core.base.dto.admin.UserChangePwdReqDTO;
import com.heshuang.core.base.dto.admin.UserOneReqDTO;
import com.heshuang.core.base.dto.admin.UserPageReqDTO;
import com.heshuang.core.base.entity.admin.UserEntity;
import com.heshuang.core.base.vo.admin.UserOneVO;


/**
 * @description:
 * @author: heshuang
 */
public interface UserService extends IService<UserEntity> {

    /**
     * 新增或修改用户
     *
     * @param reqDTO
     * @return
     */
    int saveOrUpdateUser(UserAddOrUpdateReqDTO reqDTO);

    /**
     * 改变用户状态(删除或禁用)
     *
     * @param userId
     * @param userStatus
     * @return
     */
    int changeUserStatus(String userId, Integer userStatus);


    /**
     * 根据用户ID获取用户信息
     *
     * @param reqDTO
     * @return
     */
    UserOneVO selectUserOne(UserOneReqDTO reqDTO);

   /**
     * 列表分页查询
     *
     * @param reqDTO
     * @return
     */
    IPage<UserEntity> queryUserPageList(UserPageReqDTO reqDTO);

    /**
     * 修改密码
     *
     * @param reqDTO
     * @return
     */
    int changePwd(UserChangePwdReqDTO reqDTO);
}
