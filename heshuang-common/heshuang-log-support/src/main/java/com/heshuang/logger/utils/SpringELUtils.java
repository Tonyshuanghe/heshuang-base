//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.logger.utils;

import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SpringELUtils {
    private static Map<String, Expression> expressions = new ConcurrentHashMap(16);

    public SpringELUtils() {
    }

    public static String parse(String spel, Method method, Object[] args) {
        Expression expression = getExpressionByTemplate(spel);
        if (expression == null) {
            return spel;
        } else {
            LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
            String[] paraNameArr = u.getParameterNames(method);
            new SpelExpressionParser();
            StandardEvaluationContext context = new StandardEvaluationContext();

            for(int i = 0; i < paraNameArr.length; ++i) {
                context.setVariable(paraNameArr[i], args[i]);
            }

            return (String)expression.getValue(context, String.class);
        }
    }

    public static String parse(Object rootObject, String spel, Method method, Object[] args) {
        Expression expression = getExpressionByTemplate(spel);
        if (expression == null) {
            return spel;
        } else {
            LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
            String[] paraNameArr = u.getParameterNames(method);
            new SpelExpressionParser();
            StandardEvaluationContext context = new MethodBasedEvaluationContext(rootObject, method, args, u);

            for(int i = 0; i < paraNameArr.length; ++i) {
                context.setVariable(paraNameArr[i], args[i]);
            }

            return (String)expression.getValue(context, String.class);
        }
    }

    private static Expression getExpressionByTemplate(String template) {
        if (!StringUtils.isEmpty(template) && (template.contains("#") || template.contains("'"))) {
            Expression expression = (Expression)expressions.get(template);
            if (expression != null) {
                return expression;
            } else {
                ExpressionParser expressionParser = new SpelExpressionParser();
                expression = expressionParser.parseExpression(template);
                expressions.putIfAbsent(template, expression);
                return expression;
            }
        } else {
            return null;
        }
    }
}
