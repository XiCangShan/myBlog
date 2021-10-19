package com.myall.myBlog.service;

import com.github.pagehelper.PageInfo;
import com.myall.myBlog.pojo.Article;

import java.util.HashMap;
import java.util.List;

public interface ArticleService {
    /**
     * 统计文章数
     * @param status
     * @return
     */
    Integer countArticle(Integer status);
//    统计所有评论数
    Integer countArticleComment();
//    统计所有点击数
    Integer countArticleView();
//    根据分类id 统计某个分类下的文章数
    Integer countArticleByCategoryId(Integer id);
//    根据标签id 统计某个标签下的文章数
    Integer countArticleByTagId(Integer id);
//    根据map 获取文章list
    List<Article> getArticleList(HashMap<String,Object> criteria);
//    根据用户id 获取某个用户最新文章
    List<Article> getNewArticleListByUserId(Integer userId,Integer limit);
//    按点击量降序 获取文章list
    List<Article> getViewArticleList(Integer limit);
//    按评论降序 获取文章list
    List<Article> getCommentArticleList(Integer limit);
//    随机文章list
    List<Article> getRandomArticleList(Integer limit);
//    根据分类id 获取某个分类的文章list
    List<Article> getArticleListByCategoryId(Integer categoryId,Integer limit);
//    根据分类ids 获取某个分类的文章list
    List<Article> getArticleListByCategoryIds(List<Integer> categoryIds,Integer limit);
//    根据文章id 获取某个文章的分类id list
    List<Integer> getCategoryIdListByArticleId(Integer articleId);
//    获取文章信息
    List<Article> getArticleListNotWithContent();
//    简单更新文章信息
    void updateArticle(Article article);
//    传入带有tagList和categoryList属性 用于更新 文章分类和标签关联表
    void updateArticleDetail(Article article);
    void updateCommentCount(Integer articleId);
    void delete(Integer id);
    void deleteBatch(List<Integer> ids);
//    分页 以拿到完整的Article对象
    PageInfo<Article> pageArticle(Integer pageIndex,Integer pageSize,HashMap<String,Object> criteria);
    Article getArticleByIdAndStatus(Integer id,Integer status);
    Article getArticleByAfter(Integer id);
    Article getArticleByBefore(Integer id);
//    获取随后更新的文章
    Article getArticleByLastUpdate();
    void insert(Article article);
}
