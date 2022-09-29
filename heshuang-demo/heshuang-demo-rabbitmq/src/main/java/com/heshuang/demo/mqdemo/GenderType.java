package com.heshuang.demo.mqdemo;

/**
 * @author heshuang
 * @version v1.0
 * CreationTime: - 2022/4/15 14:08
 * Description:
 */
public enum GenderType {
    MAN("1", "男", ""),
    WOMEN("0", "女", "");
    private String value;
    private String desc;
    private String code;

    GenderType(String value, String code, String desc) {
        this.value = value;
        this.desc = desc;
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public String getCode() {
        return this.code;
    }

    public static GenderType of(String value) {
        for (GenderType item : values()) {
            if (item.getValue().equals(value)) {
                return item;
            }
        }
        return null;
    }

    public static GenderType ofByCode(String code) {
        for (GenderType item : values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return null;
    }

    public static String value(String value) {
        GenderType item = of(value);
        if (item == null) {
            return null;
        }
        return item.getCode();
    }


    public static String codeOf(String value) {
        GenderType item = of(value);
        if (item == null) {
            return null;
        }
        return item.getCode();
    }

    public static String descOf(String value) {
        GenderType item = of(value);
        if (item == null) {
            return "";
        }
        return item.getDesc();
    }
}
