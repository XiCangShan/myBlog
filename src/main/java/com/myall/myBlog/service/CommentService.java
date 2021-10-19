package com.myall.myBlog.service;

import com.github.pagehelper.PageInfo;
import com.myall.myBlog.pojo.Comment;

import java.util.HashMap;
import java.util.List;

public interface CommentService {
    void insert(Comment comment);

    Comment getCommentById(Integer id);

    List<Comment> getCommentListByArticleId(Integer articleId);

    //    获取某个用户发表文章所收到的评论list
    List<Comment> getCommentListByUserId(Integer userId);

    //    获取某个用户发表文章所收到的评论list 按时间降序
    List<Comment> getNewCommentListByUserId(Integer userId, Integer limit);

    //    获取子评论
    List<Comment> getChildCommentListById(Integer id);

    //    获取某个用户发表文章所收到的评论list 分页 不传userId为全查
    PageInfo<Comment> getCommentListByPage(Integer pageIndex,
                                           Integer pageSize,
                                           Integer userId);

    //    获取某个用户发表文章所收到的评论list 分页 不传userId不行
    PageInfo<Comment> getCommentListByPageAndUserId(Integer pageIndex,
                                                    Integer pageSize,
                                                    Integer userId);

    void delete(Integer id);

    void update(Comment comment);

    int count();

}
