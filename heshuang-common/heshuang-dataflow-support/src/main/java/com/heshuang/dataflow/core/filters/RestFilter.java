//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.dataflow.core.filters;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.heshuang.dataflow.core.IDataFilter;
import com.heshuang.dataflow.core.context.DataContextHolder;
import com.heshuang.dataflow.utils.HttpUtils;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class RestFilter implements IDataFilter {
    private Expression expression;

    private EvaluationContext evaluationContext;

    private Class queryClass;

    ExpressionParser parser = (ExpressionParser)new SpelExpressionParser();

    private String method = "GET";

    public RestFilter(String method, String queryTpl, Class queryClass) {
        this.method = method;
        this.expression = this.parser.parseExpression(queryTpl);
        this.queryClass = queryClass;
    }

    public static RestFilter of(String method, String queryTpl, Class queryClass) {
        return new RestFilter(method, queryTpl, queryClass);
    }
    @Override
    public List<Object> filter(List<Object> args) {
        this.evaluationContext = (EvaluationContext)new StandardEvaluationContext();
        this.evaluationContext.setVariable("data", args);
        Map<String, Object> map = DataContextHolder.getContext().requestMap();
        if (map != null) {
            Iterator<String> var3 = map.keySet().iterator();
            while (var3.hasNext()) {
                String str = var3.next();
                this.evaluationContext.setVariable(str, map.get(str));
            }
        }
        String query = String.format("%s", new Object[] { this.expression.getValue(this.evaluationContext) });
        String result = "";
        String queryParam = null;
        if (this.expression != null)
            queryParam = String.format("%s", new Object[] { this.expression.getValue() });
        if ("POST".equalsIgnoreCase(this.method)) {
            result = HttpUtils.sendPost(query, queryParam);
        } else {
            result = HttpUtils.sendGet(query, queryParam);
        }
        return (this.queryClass != null) ?
                JSON.parseArray(result, this.queryClass) :
                Lists.newArrayList(new Object[] { JSON.parseObject(result) });
    }
}
