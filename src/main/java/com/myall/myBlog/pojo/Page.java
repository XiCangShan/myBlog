package com.myall.myBlog.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Page implements Serializable {

    private static final long serialVersionUID = -3559531321694235271L;
    private Integer pageId;
    private String pageKey;
    private String pageTitle;
    private String pageContent;
    private Date pageCreateTime;
    private Date pageUpdateTime;
    private Integer pageViewCount;
    private Integer pageCommentCount;
    private Integer pageStatus;
}
