package com.myall.myBlog.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ArticleCategoryRef implements Serializable {

    private static final long serialVersionUID = -652529619160629273L;
    private Integer articleId;

    private Integer categoryId;

    public ArticleCategoryRef() {
    }

    public ArticleCategoryRef(Integer articleId, Integer categoryId) {
        this.articleId = articleId;
        this.categoryId = categoryId;
    }
}
