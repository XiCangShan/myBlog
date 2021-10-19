package com.myall.myBlog.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Comment implements Serializable {

    private static final long serialVersionUID = -3118385513883710744L;
//    评论id
    private Integer commentId;
//    评论父评论id
    private Integer commentPid;
//    评论父评论人名称
    private String commentPname;
//    文章id
    private Integer commentArticleId;
//    评论人名字
    private String commentAuthorName;
//    评论人邮箱
    private String commentAuthorEmail;
//    评论人主页
    private String commentAuthorUrl;
//    评论恩物头像
    private String commentAuthorAvatar;
//    内容
    private String commentContent;
//    浏览器信息
    private String commentAgent;

    private String commentIp;

    private Date commentCreateTime;
//     角色(管理员1，访客0)
    private Integer commentRole;
//     评论用户ID
    private Integer commentUserId;
//     非数据库字段 评论所在文章
    private Article article;

}
