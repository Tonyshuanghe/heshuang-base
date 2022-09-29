package com.heshuang.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heshuang.admin.mapper.MenuMapper;
import com.heshuang.admin.service.MenuService;
import com.heshuang.core.base.entity.admin.MenuEntity;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: heshuang
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, MenuEntity> implements MenuService {
}
