package com.myall.myBlog.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class Category implements Serializable {
    private static final long serialVersionUID = -6378470132425905251L;
    private Integer categoryId;
    private Integer categoryPid;
    private String categoryName;
    private String categoryDescription;
    private Integer categoryOrder;
    private String categoryIcon;
    /**
     * 文章数量(非数据库字段)
     */
    private Integer articleCount;
    public Category() {
    }

    public Category(Integer categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }
    public Category(Integer categoryId) {
        this.categoryId = categoryId;
    }
    public static Category Default(){
        return new Category(100000000,"未分类");
    }
}
