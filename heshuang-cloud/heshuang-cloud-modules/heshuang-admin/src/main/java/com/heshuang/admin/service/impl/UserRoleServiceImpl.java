package com.heshuang.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heshuang.admin.mapper.UserRoleMapper;
import com.heshuang.admin.service.UserRoleService;
import com.heshuang.core.base.entity.admin.UserRoleEntity;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: heshuang
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRoleEntity> implements UserRoleService {
}
