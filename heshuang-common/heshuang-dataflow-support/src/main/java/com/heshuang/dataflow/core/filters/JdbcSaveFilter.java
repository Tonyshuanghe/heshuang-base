//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.dataflow.core.filters;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.heshuang.core.base.utils.CommonUtils;
import com.heshuang.dataflow.core.context.DataContextHolder;
import com.heshuang.dataflow.core.dao.EntityBaseDao;

import java.util.List;
import org.apache.commons.lang3.StringUtils;

public class JdbcSaveFilter extends AbstractDataFilter {
    private String saveKey;
    private Class queryClass;
    private EntityBaseDao service;
    private String sqlTpl;

    public JdbcSaveFilter(String saveKey, Class queryClass) {
        this.saveKey = saveKey;
        this.queryClass = queryClass;
        this.init();
    }

    public JdbcSaveFilter(String saveKey, Class queryClass, String sql) {
        this.saveKey = saveKey;
        this.queryClass = queryClass;
        this.sqlTpl = sql;
        this.init();
    }

    public static JdbcSaveFilter of(String saveKey, Class queryClass, String sql) {
        return new JdbcSaveFilter(saveKey, queryClass, sql);
    }

    public void init() {
        this.service = (EntityBaseDao) SpringUtil.getBean(EntityBaseDao.class);
    }
    @Override
    public List<Object> filter(List<Object> args) {
        if (StringUtils.isNotBlank(this.saveKey)) {
            args = DataContextHolder.getContext().getData(this.saveKey);
        }

        if (CommonUtils.isNotEmpty(args)) {
            args = BeanUtil.copyToList(args, this.queryClass);
            if (StringUtils.isNotBlank(this.sqlTpl)) {
                this.service.updateSql(this.sqlTpl, args);
            } else {
                this.service.save(args);
            }
        }

        return args;
    }
}
