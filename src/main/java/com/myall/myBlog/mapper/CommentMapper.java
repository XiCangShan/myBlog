package com.myall.myBlog.mapper;

import com.myall.myBlog.pojo.Comment;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

public interface CommentMapper {
    //    根据id删除
    int deleteById(Integer id);

    int insert(Comment comment);

    //    根据id获取评论
    Comment getCommentById(Integer id);

    int update(Comment comment);

    //    根据文章id获取评论列表
    List<Comment> getCommentByArticleId(Integer articleId);

    //    根据文章id List获取评论列表
    List<Comment> getCommentByArticleIds(@Param("ids") List<Integer> ids);

    //    根据userId获取评论列表，可以为null
    List<Comment> getCommentByUserId(@Param("userId") Integer userId);

    //    统计评论数
    int countComment();

    //    获取最新评论
    List<Comment> getNewComment(@Param("limit") Integer limit, @Param("userId") Integer userId);

    //    获取子评论
    List<Comment> getChildComment(Integer id);

    //    根据用户id删除评论
    int deleteByUserId(Integer userId);

    //    根据文章id删除评论
    int deleteByArticleId(Integer articleId);

    void updateCommentAvatarById(Integer id);
}
