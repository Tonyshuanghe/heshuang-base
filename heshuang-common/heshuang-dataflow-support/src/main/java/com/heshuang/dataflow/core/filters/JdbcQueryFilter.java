//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.dataflow.core.filters;

import cn.hutool.extra.spring.SpringUtil;
import com.heshuang.dataflow.core.context.DataContextHolder;
import com.heshuang.dataflow.core.dao.EntityBaseDao;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class JdbcQueryFilter extends AbstractDataFilter {
    private EntityBaseDao entityBaseDao;
    private Expression expression;
    private EvaluationContext evaluationContext;
    private Class queryClass;
    ExpressionParser parser = new SpelExpressionParser();

    public JdbcQueryFilter(String sqlTemplate, Class queryClass) {
        this.init(sqlTemplate, queryClass);
    }

    public <T> void init(String sqlTemplate, Class<T> queryClass) {
        this.expression = this.parser.parseExpression(sqlTemplate);
        this.queryClass = queryClass;
        this.entityBaseDao = (EntityBaseDao) SpringUtil.getBean(EntityBaseDao.class);
    }
    @Override
    public List<Object> filter(List<Object> args) {
        this.evaluationContext = new StandardEvaluationContext();
        this.evaluationContext.setVariable("data", args);
        Map<String, Object> map = DataContextHolder.getContext().requestMap();
        if (map != null) {
            Iterator var3 = map.keySet().iterator();

            while(var3.hasNext()) {
                String key = (String)var3.next();
                this.evaluationContext.setVariable(key, map.get(key));
            }
        }

        String sql = String.format("%s", this.expression.getValue(this.evaluationContext));
        return this.entityBaseDao.nativeQueryList(this.queryClass, sql, (Object[])null);
    }
}
