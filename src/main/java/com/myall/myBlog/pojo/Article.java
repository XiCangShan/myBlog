package com.myall.myBlog.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class Article implements Serializable {

    private static final long serialVersionUID = 2388574319861188182L;
    //文章id
    private Integer articleId;
    //文章作者id
    private Integer articleUserId;
    //文章标题
    private String articleTitle;
    //观看次数
    private Integer articleViewCount;

    private Integer articleCommentCount;

    private Integer articleLikeCount;

    private Date articleCreateTime;

    private Date articleUpdateTime;

    private Integer articleIsComment;
    //是否已发布/草稿
    private Integer articleStatus;
    //文章权重
    private Integer articleOrder;
    //文章内容
    private String articleContent;

    private String articleSummary;

    private String articleThumbnail;
    //作者
    private User user;
    //tagList
    private List<Tag> tagList;
    //分类list
    private List<Category> categoryList;
}
