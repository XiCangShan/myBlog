package com.myall.myBlog.enums;

public enum UserRole
{
    ADMIN("admin","管理"),
    USER("user","用户");
    private String value;
    private String massage;

    UserRole(String value, String massage) {
        this.value = value;
        this.massage = massage;
    }

    public String getValue() {
        return value;
    }
}
