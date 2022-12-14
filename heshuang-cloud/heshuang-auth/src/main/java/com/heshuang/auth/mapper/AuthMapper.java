package com.heshuang.auth.mapper;

import com.heshuang.core.base.vo.auth.UserAuthVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @description: 认证
 * @author: heshuang
 */
@Repository
public interface AuthMapper {

    /**
     * 根据不同账户及其账户类型获取对应的用户信息
     *
     * @param type
     * @param account
     * @return
     */
    UserAuthVO selectUserAuthInfo(@Param("type") Integer type, @Param("account") String account);

    /**
     * 根据用户ID获取对应的角色key列表
     *
     * @param userId
     * @return
     */
    List<String> selectUserIdByRole(@Param("userId") String userId);


    /**
     * 根据用户ID获取对应的角色所属菜单key
     *
     * @param userId
     * @return
     */
    List<String> selectUserIdByPerm(@Param("userId") String userId);
}
