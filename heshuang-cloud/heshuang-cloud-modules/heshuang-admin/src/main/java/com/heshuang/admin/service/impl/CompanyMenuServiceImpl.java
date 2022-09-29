package com.heshuang.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heshuang.admin.mapper.CompanyMenuMapper;
import com.heshuang.admin.service.CompanyMenuService;
import com.heshuang.core.base.entity.admin.CompanyMenuEntity;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: heshuang
 */
@Service
public class CompanyMenuServiceImpl extends ServiceImpl<CompanyMenuMapper, CompanyMenuEntity> implements CompanyMenuService {
}
