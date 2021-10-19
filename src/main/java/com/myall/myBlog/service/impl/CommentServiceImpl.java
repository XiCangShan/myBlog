package com.myall.myBlog.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.myall.myBlog.enums.ArticleCommentStatus;
import com.myall.myBlog.enums.ArticleStatus;
import com.myall.myBlog.mapper.ArticleMapper;
import com.myall.myBlog.mapper.CommentMapper;
import com.myall.myBlog.mapper.UserMapper;
import com.myall.myBlog.pojo.Article;
import com.myall.myBlog.pojo.Category;
import com.myall.myBlog.pojo.Comment;
import com.myall.myBlog.service.CategoryService;
import com.myall.myBlog.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    ArticleMapper articleMapper;
    @Autowired
    UserMapper userMapper;
    @Override
    public void insert(Comment comment) {

        comment.setCommentCreateTime(new Date());
        try {
            commentMapper.insert(comment);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("添加评论失败 comment:{},catch:{}", comment, e);
        }
    }
    public Comment getCommentById(Integer id){
       Comment comment = null;
        try {
            comment = commentMapper.getCommentById(id);
            commentMapper.updateCommentAvatarById(comment.getCommentId());

        } catch (Exception e) {
            e.printStackTrace();
            log.error("根据id获取评论失败，articleId:{},cause:{}", id, e);
        }
        return comment;
    }
    @Override
    public List<Comment> getCommentListByArticleId(Integer articleId) {
        List<Comment> comments = null;
        try {
            comments = commentMapper.getCommentByArticleId(articleId);
            for (Comment c:comments) {
                commentMapper.updateCommentAvatarById(c.getCommentId());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("根据文章id获取评论失败，articleId:{},cause:{}", articleId, e);
        }
        return comments;
    }

    @Override
    public List<Comment> getCommentListByUserId(Integer userId) {
        List<Comment> comments = null;
        try {
            comments = commentMapper.getCommentByUserId(userId);
            for (Comment c:comments) {
                commentMapper.updateCommentAvatarById(c.getCommentId());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("根据用户id获取评论失败，userId:{},cause:{}", userId, e);
        }
        return comments;
    }

    @Override
    public List<Comment> getNewCommentListByUserId(Integer userId, Integer limit) {
        List<Comment> commentList = null;
        try {
            commentList = commentMapper.getNewComment(limit, userId);
            for (int i = 0; i < commentList.size(); i++) {
                Article article = articleMapper.getArticleByStatusAndId(ArticleStatus.PUBLISH.getValue(), commentList.get(i).getCommentArticleId());
                commentList.get(i).setArticle(article);
                commentMapper.updateCommentAvatarById(commentList.get(i).getCommentId());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("根据用户id获取最新评论失败，articleId:{},cause:{}", userId, e);
        }
        return commentList;
    }

    @Override
    public List<Comment> getChildCommentListById(Integer id) {
        List<Comment> comments = null;
        try {
            comments = commentMapper.getChildComment(id);
            for (Comment c:comments) {
                commentMapper.updateCommentAvatarById(c.getCommentId());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("根据父id获取子评论失败，id:{},cause:{}", id, e);
        }
        return comments;
    }

    @Override
    public PageInfo<Comment> getCommentListByPage(Integer pageIndex, Integer pageSize, Integer userId) {
        PageHelper.startPage(pageIndex, pageSize);
        List<Comment> comments = null;
        try {
            comments = commentMapper.getCommentByUserId(userId);
            for (int i = 0; i < comments.size(); i++) {
                Comment comment = comments.get(i);
                Article article = articleMapper.getArticleByStatusAndId(ArticleStatus.PUBLISH.getValue(), comment.getCommentArticleId());
                comment.setArticle(article);
                commentMapper.updateCommentAvatarById(comment.getCommentId());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("根据userId(可为空)获取分页评论失败，userId:{},cause:{}", userId, e);
        }
        return new PageInfo<>(comments);
    }

    @Override
    public PageInfo<Comment> getCommentListByPageAndUserId(Integer pageIndex, Integer pageSize, Integer userId) {
        PageHelper.startPage(pageIndex, pageSize);
        List<Comment> comments = null;
        try {
            List<Integer> articleIds = articleMapper.getArticleIdsByUserId(userId);
            if (articleIds != null && articleIds.size() > 0){
//为每一个comment封装article
                comments = commentMapper.getCommentByArticleIds(articleIds);
                for (int i = 0; i < comments.size(); i++) {
                    Comment comment = comments.get(i);
                    Article article = articleMapper.getArticleByStatusAndId(ArticleStatus.PUBLISH.getValue(), comment.getCommentArticleId());
                    comment.setArticle(article);
                    commentMapper.updateCommentAvatarById(comment.getCommentId());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("根据userId获取分页评论失败，userId:{},cause:{}", userId, e);
        }
        return new PageInfo<>(comments);
    }

    @Override
    public void delete(Integer id) {
        try {
            commentMapper.deleteById(id);
        }catch (Exception e){
            e.printStackTrace();
            log.error("根据id删除评论失败，id:{},cause:{}", id, e);
        }
    }

    @Override
    public void update(Comment comment) {
        try {
            commentMapper.update(comment);
        }catch (Exception e){
            e.printStackTrace();
            log.error("更新评论失败，comment:{},cause:{}", comment, e);
        }
    }




    @Override
    public int count() {
        int count = 0;
        try {
            count = commentMapper.countComment();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("统计失败 catch:{}", e);
        }
        return count;
    }
}
