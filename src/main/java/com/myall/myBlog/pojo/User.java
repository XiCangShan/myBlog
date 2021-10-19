package com.myall.myBlog.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author liuyanzhao
 */
@Data
public class User implements Serializable{
    private static final long serialVersionUID = -2783186720235995137L;

    private Integer userId;

    private String userName;

    private String userPass;
    //昵称
    private String userNickname;

    private String userEmail;

    private String userUrl;
    //头像地址
    private String userAvatar;
    //最后登录ip
    private String userLastLoginIp;
    //注册时间
    private Date userRegisterTime;
    //最后登录时间
    private Date userLastLoginTime;
//  1:正常 0：封禁
    private Integer userStatus;

    /**
     * 用户角色：admin/user
     */
    private String userRole;

    /**
     * 文章数量（不是数据库字段）
     */
    private Integer articleCount;

}