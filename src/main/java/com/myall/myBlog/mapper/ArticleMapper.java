package com.myall.myBlog.mapper;

import com.myall.myBlog.pojo.Article;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

public interface ArticleMapper {
    //    添加文章
    int insert(Article article);
    //    根据文章id删除
    int deleteByArticleId(Integer articleId);
    //    根据用户id删除
    int deleteByArticleUserId(Integer articleUserId);
    //    修改
    int update(Article article);
    //    统计所有文章数根据状态码
    int countArticleByStatus(@Param("status") Integer status);
    //    统计所有评论数
    int countArticleComment();
    //    统计所有点击量
    int countArticleView();
    //    根据状态码和文章id获取文章
//    根据用户id获取文章list
    List<Article> getArticleListByArticleUserId(Integer articleUserId);
    //    根据条件map获取文章
    List<Article> getArticleListByMap(HashMap<String,Object> map);
    //    获取所有文章不包含内容
    List<Article> getArticleListByAllNotWithContent();
    //    获取所有文章
    List<Article> getArticleList();
    //    根据文章id获取文章
    Article getArticleByStatusAndId(@Param(value = "status") Integer status, @Param(value = "id") Integer id);
    //    按点击量获取文章
    List<Article> getArticleByViewCount(@Param("limit")Integer limit);
    //    根据id获取下一个文章
    Article getArticleByAfterId(@Param("id")Integer id);
    Article getArticleByBeforeId(@Param("id") Integer id);
    //    获取随机文章
    List<Article> getRandomArticle(@Param("limit")Integer limit);
    //    根据评论数获取文章
    List<Article> getArticleByCommentCount(@Param("limit")Integer limit);
    //    根据文章id更新评论数
    void updateCommentCountByArticleId(@Param("articleId") Integer articleId);
    //    获取最后更新的文章
    Article getLastUpdateArticle();
    //    根据用户id获取文章数
    int countArticleByUserId(@Param("userId") Integer userId);
    //    根据分类id获取文章
    List<Article> getArticleByCategoryId(@Param("categoryId") Integer categoryId,
                                         @Param("limit") Integer limit);
    //    根据分类id的list获取文章
    List<Article> getArticleByCategoryIds(@Param("categoryIds") List<Integer> categoryIds,
                                         @Param("limit") Integer limit);
    //    根据UserId获取文章
    List<Article> getArticleByLimit(@Param("userId") Integer userId, @Param("limit") Integer limit);
    //    批量删除文章
    Integer deleteBatch(@Param("ids") List<Integer> ids);
    //    根据用户id获取文章id List
    List<Integer> getArticleIdsByUserId(@Param("userId") Integer userId);
}
