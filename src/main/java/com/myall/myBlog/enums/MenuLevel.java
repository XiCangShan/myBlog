package com.myall.myBlog.enums;

public enum MenuLevel {
    TOP_MENU(1,"顶部菜单"),
    MAIN_MENU(2,"主要菜单");
    private Integer code;
    private String mag;

    MenuLevel(Integer code, String mag) {
        this.code = code;
        this.mag = mag;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMag() {
        return mag;
    }

    public void setMag(String mag) {
        this.mag = mag;
    }
}
