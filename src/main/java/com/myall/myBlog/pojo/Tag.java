package com.myall.myBlog.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class Tag implements Serializable {
    private static final long serialVersionUID = 2766428131817840071L;
    private int tagId;
    private String tagName;
    private String tagDescription;
    /**
     * 文章数量(不是数据库字段)
     */
    private Integer articleCount;

    public Tag(Integer tagId) {
        this.tagId=tagId;
    }
}
