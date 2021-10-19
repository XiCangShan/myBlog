package com.myall.myBlog.dto;

import lombok.Data;

@Data
public class JsonResult<T> {
    private  Integer code;
    private  String msg;
    private T data;

    public JsonResult() {
    }

    public JsonResult(Integer code, String msg, T data) {
//        0:成功
//        1：失败
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public JsonResult fail(){
        return new JsonResult(1,"操作失败",null);
    }
    public JsonResult fail(String msg){
        return new JsonResult(1,msg,null);
    }
    public JsonResult ok(){
        return new JsonResult(0,"操作成功",null);
    }
    public JsonResult ok(T data){
        return new JsonResult(0,"操作成功",data);
    }
}
