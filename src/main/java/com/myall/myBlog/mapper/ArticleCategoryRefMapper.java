package com.myall.myBlog.mapper;

import com.myall.myBlog.pojo.ArticleCategoryRef;
import com.myall.myBlog.pojo.Category;

import java.util.List;

public interface ArticleCategoryRefMapper {
    int insert(ArticleCategoryRef articleCategoryRef);
//    根据文章id删除
    int deleteByArticleId(Integer articleId);
//    根据分类id删除
    int deleteByCategoryId(Integer categoryId);
//    根据分类id统计文章数
    int countArticleByCategoryId(Integer categoryId);
//    根据分类id获得文章id list
    List<Integer> getArticleIdListByCategoryId(Integer categoryId);
//    根据文章id获取分类id list
    List<Integer> getCategoryIdListByArticleId(Integer articleId);
//    根据文章id获取分类   list
    List<Category> getCategoryListByArticleId(Integer articleId);
}
