//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.dict.conts;

public enum SteamDataType {
    NONE(-1, "", ""),
    ONLINE(1, "stream:devi:now:%s:online", "在线状态"),
    INUSE(2, "stream:devi:now:%s:inuse", "使用状态"),
    OPENED(3, "stream:devi:now:%s:opened", "开关屏状态"),
    TIME(4, "stream:devi:now:%s:time", "更新时间"),
    SYNC(5, "stream:devi:now:%s:sync", "同步"),
    DATA(6, "stream:devi:now:%s:data", "实时数据");

    private String tpl;
    private String desc;
    private Integer code;

    private SteamDataType(Integer code, String tpl, String desc) {
        this.tpl = tpl;
        this.desc = desc;
        this.code = code;
    }

    public String getTpl() {
        return this.tpl;
    }

    public void setTpl(String tpl) {
        this.tpl = tpl;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
