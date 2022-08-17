package com.fanbo.enums;

/**
 * 日期偏移类型
 * @author FanBo
 */
public enum DateOffsetType {
    /**
     * 月
     */
    M("M", "月"),
    /**
     * 天
     */
    D("D", "天"),
    /**
     * 分钟
     */
    MIN("D", "分钟");;

    /**
     * 代码
     */
    private String code;
    /**
     * 名称
     */
    private String name;

    private DateOffsetType(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static DateOffsetType toEnum(Integer code) {
        if (code != null) {
            for (DateOffsetType item : DateOffsetType.values()) {
                if (item.getCode().equals(code)) {
                    return item;
                }
            }
        }
        return null;
    }
}
