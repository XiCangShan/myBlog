package com.myall.myBlog.controller.home;

import cn.hutool.http.HtmlUtil;
import com.myall.myBlog.dto.JsonResult;
import com.myall.myBlog.enums.ArticleStatus;
import com.myall.myBlog.enums.CommentRole;
import com.myall.myBlog.pojo.Article;
import com.myall.myBlog.pojo.Comment;
import com.myall.myBlog.pojo.Notice;
import com.myall.myBlog.pojo.User;
import com.myall.myBlog.service.ArticleService;
import com.myall.myBlog.service.CommentService;
import com.myall.myBlog.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import static cn.hutool.extra.servlet.ServletUtil.getClientIP;

@RestController


public class CommentController {
    @Autowired
    CommentService commentService;
    @Autowired
    ArticleService articleService;

    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public JsonResult insertComment(HttpSession session, HttpServletRequest request, Comment comment) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return new JsonResult().fail("请先登录");
        }
        Article article = articleService.getArticleByIdAndStatus(comment.getCommentArticleId(), ArticleStatus.PUBLISH.getValue());
        if (article == null) {
            return new JsonResult().fail("文章不存在");
        }
//设置用户id
        comment.setCommentUserId(user.getUserId());
//        设置用户ip
        comment.setCommentIp(getClientIP(request));
//        如果是博主评论
        if (user.getUserId().equals(article.getArticleUserId())) {
            comment.setCommentRole(CommentRole.OWNER.getValue());
        }
//        设置评论头像
        comment.setCommentAuthorAvatar(user.getUserAvatar());
//过滤字符，防止xss攻击
        comment.setCommentContent(HtmlUtil.escape(comment.getCommentContent()));
        comment.setCommentAuthorEmail(user.getUserEmail());
        comment.setCommentAuthorName(user.getUserNickname());
        comment.setCommentAuthorUrl(user.getUserUrl());
        try {
            commentService.insert(comment);
//            更新评论总数
            articleService.updateCommentCount(article.getArticleId());
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult().fail();
        }
        return new JsonResult().ok();
    }
}
