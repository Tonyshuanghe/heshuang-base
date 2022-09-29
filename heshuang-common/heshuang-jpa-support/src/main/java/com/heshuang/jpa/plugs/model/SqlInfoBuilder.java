//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.jpa.plugs.model;


import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import com.heshuang.jpa.consts.LikeTypeEnum;
import com.heshuang.jpa.plugs.utils.StringHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SqlInfoBuilder {
    private static final Logger log = LoggerFactory.getLogger(SqlInfoBuilder.class);
    protected SqlInfo sqlInfo;
    protected Object context;
    protected String prefix;
    private String symbol;
    private Map<String, Object> others;

    SqlInfoBuilder(BuildSource source) {
        this.sqlInfo = source.getSqlInfo();
        this.context = source.getContext();
        this.prefix = source.getPrefix();
        this.symbol = source.getSymbol();
        this.others = source.getOthers();
    }

    private void doPrependWhere() {
        if (this.sqlInfo.isPrependWhere()) {
            this.sqlInfo.getJoin().append(" WHERE ");
            if (" AND ".equalsIgnoreCase(this.prefix) || " OR ".equalsIgnoreCase(this.prefix)) {
                this.prefix = "";
            }

            this.sqlInfo.setPrependWhere(false);
        }

    }

    public void buildNormalSql(String fieldText, String name, Object value) {
        this.doPrependWhere();
        this.sqlInfo.getJoin().append(this.prefix).append(fieldText).append(this.symbol).append(":").append(name);
        this.sqlInfo.getParams().put(name, value);
    }

    public void buildLikeSql(String fieldText, String name, Object value) {
        this.doPrependWhere();
        this.sqlInfo.getJoin().append(this.prefix).append(fieldText).append(StringUtils.isBlank(this.symbol) ? " LIKE " : this.symbol).append(":").append(name);
        if (this.others != null && this.others.size() != 0) {
            LikeTypeEnum likeTypeEnum = (LikeTypeEnum)this.others.get("type");
            if (likeTypeEnum == LikeTypeEnum.STARTS_WITH) {
                this.sqlInfo.getParams().put(name, value + "%");
            } else if (likeTypeEnum == LikeTypeEnum.ENDS_WITH) {
                this.sqlInfo.getParams().put(name, "%" + value);
            }

        } else {
            this.sqlInfo.getParams().put(name, "%" + value + "%");
        }
    }

    public void buildLikePatternSql(String fieldText, String pattern) {
        this.doPrependWhere();
        this.sqlInfo.getJoin().append(this.prefix).append(fieldText).append(StringHelper.isBlank(this.symbol) ? " LIKE " : this.symbol).append("'").append(pattern).append("'");
    }

    public void buildBetweenSql(String fieldText, String startText, Object startValue, String endText, Object endValue) {
        if (startValue == null && endValue == null) {
            log.warn("between 区间查询的开始值和结束值均为 null，将直接跳过.");
        } else {
            this.doPrependWhere();
            String endNamed;
            if (startValue != null && endValue == null) {
                endNamed = StringHelper.fixDot(startText);
                this.sqlInfo.getJoin().append(this.prefix).append(fieldText).append(" >= ").append(":").append(endNamed);
                this.sqlInfo.getParams().put(endNamed, startValue);
            } else if (startValue == null) {
                endNamed = StringHelper.fixDot(endText);
                this.sqlInfo.getJoin().append(this.prefix).append(fieldText).append(" <= ").append(":").append(endNamed);
                this.sqlInfo.getParams().put(endNamed, endValue);
            } else {
                String startNamed = StringHelper.fixDot(startText);
                endNamed = StringHelper.fixDot(endText);
                this.sqlInfo.getJoin().append(this.prefix).append(fieldText).append(" BETWEEN ").append(":").append(startNamed).append(" AND ").append(":").append(endNamed);
                Map<String, Object> params = this.sqlInfo.getParams();
                params.put(startNamed, startValue);
                params.put(endNamed, endValue);
            }

        }
    }

    public void buildInSql(String fieldText, String name, Object obj) {
        this.doPrependWhere();
        this.sqlInfo.getJoin().append(this.prefix).append(fieldText).append(this.symbol).append(":").append(name);
        if (obj instanceof Collection) {
            this.sqlInfo.getParams().put(name, obj);
        } else if (obj.getClass().isArray()) {
            this.sqlInfo.getParams().put(name, Arrays.asList((Object[])obj));
        } else {
            this.sqlInfo.getParams().put(name, Collections.singletonList(obj));
        }

    }

    public void buildIsNullSql(String fieldText) {
        this.doPrependWhere();
        this.sqlInfo.getJoin().append(this.prefix).append(fieldText).append(this.symbol);
    }

    public SqlInfo getSqlInfo() {
        return this.sqlInfo;
    }

    public Object getContext() {
        return this.context;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public String getSymbol() {
        return this.symbol;
    }

    public Map<String, Object> getOthers() {
        return this.others;
    }
}
