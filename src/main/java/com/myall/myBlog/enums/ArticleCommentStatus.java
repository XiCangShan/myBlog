package com.myall.myBlog.enums;

public enum ArticleCommentStatus {
    ALLOW(1,"允许"),
    NOT_ALLOW(0,"不允许");
    private Integer code;
    private String msg;

    ArticleCommentStatus(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
