package com.myall.myBlog.pojo;

import java.io.Serializable;

public class ArticleTagRef implements Serializable {

    private static final long serialVersionUID = 2236244937469666655L;
    private Integer articleId;

    private Integer tagId;

    public ArticleTagRef() {
    }

    public ArticleTagRef(Integer articleId, Integer tagId) {
        this.articleId = articleId;
        this.tagId = tagId;
    }
}
