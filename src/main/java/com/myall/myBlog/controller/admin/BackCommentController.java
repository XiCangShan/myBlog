package com.myall.myBlog.controller.admin;

import cn.hutool.http.HtmlUtil;
import cn.hutool.http.HttpUtil;
import com.github.pagehelper.PageInfo;
import com.myall.myBlog.enums.ArticleStatus;
import com.myall.myBlog.enums.CommentRole;
import com.myall.myBlog.enums.UserRole;
import com.myall.myBlog.pojo.Article;
import com.myall.myBlog.pojo.Comment;
import com.myall.myBlog.pojo.User;
import com.myall.myBlog.service.ArticleService;
import com.myall.myBlog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin/comment")
public class BackCommentController {
    @Autowired
    CommentService commentService;
    @Autowired
    ArticleService articleService;
    @RequestMapping("")
    public String index(@RequestParam(defaultValue = "1")Integer pageIndex,
                        @RequestParam(defaultValue = "10")Integer pageSize,
                        Model model,
                        HttpSession session){
        User user=(User) session.getAttribute("user");
        Integer userId=null;
        if(!user.getUserRole().equals(UserRole.ADMIN.getValue())){
            userId=user.getUserId();
        }
        PageInfo<Comment> pageInfo = commentService.getCommentListByPage(pageIndex,pageSize,userId);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("pageUrlPrefix","comment?pageIndex");
        return "Admin/Comment/index";
    }
    @RequestMapping("/reply/{id}")
    public String reply(@PathVariable("id")Integer id,Model model){
        Comment comment = commentService.getCommentById(id);
        model.addAttribute("comment",comment);
        return "Admin/Comment/reply";
    }
    @RequestMapping(value = "/replySubmit",method = RequestMethod.POST)
    public String replySubmit(Comment comment, HttpSession session, HttpServletRequest request){
        User user = (User) session.getAttribute("user");
        Article article=articleService.getArticleByIdAndStatus(comment.getCommentArticleId(), ArticleStatus.PUBLISH.getValue());
        if(article==null){
            return "redirect:/404";
        }
        comment.setCommentUserId(user.getUserId());
        comment.setCommentContent(HtmlUtil.escape(comment.getCommentContent()));
        comment.setCommentAuthorUrl(user.getUserUrl());
        comment.setCommentAuthorName(user.getUserNickname());
        comment.setCommentAuthorEmail(user.getUserEmail());
        comment.setCommentAuthorAvatar(user.getUserAvatar());
        comment.setCommentIp(HttpUtil.getClientIP(request));
        article.setArticleCommentCount(article.getArticleCommentCount()+1);
        articleService.updateArticle(article);
        if(user.getUserId().equals(article.getArticleUserId())){
            comment.setCommentRole(CommentRole.OWNER.getValue());
        }else{
            comment.setCommentRole(CommentRole.VISITOR.getValue());
        }
        commentService.insert(comment);
        return "redirect:/admin/comment";
    }
    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable("id")Integer id,Model model){
        Comment comment = commentService.getCommentById(id);
        model.addAttribute("comment",comment);
        return "Admin/Comment/edit";
    }

    @RequestMapping(value = "/editSubmit",method = RequestMethod.POST)
    public String editSubmit(Comment comment){
        commentService.update(comment);
        return "redirect:/admin/comment";
    }

    @RequestMapping("/delete/{id}")
    public void delete(@PathVariable("id") Integer id,HttpSession session){
        User user=(User) session.getAttribute("user");
        Comment comment=commentService.getCommentById(id);

        if(!user.getUserRole().equals(UserRole.ADMIN.getValue()) && !comment.getCommentUserId().equals(user.getUserId())){
            return ;
        }
        commentService.delete(id);
//        删除子评论
        List<Comment> childCommentList = commentService.getChildCommentListById(id);
        for (Comment c:childCommentList) {
            commentService.delete(c.getCommentId());
        }
//        更新文章评论数
        Article article=articleService.getArticleByIdAndStatus(comment.getCommentArticleId(),null);
        articleService.updateCommentCount(article.getArticleId());

    }

    @RequestMapping("/receive")
    public String receive(@RequestParam(defaultValue = "1")Integer pageIndex,
                          @RequestParam(defaultValue = "10")Integer pageSize,
                          Model model,
                          HttpSession session){
        User user=(User) session.getAttribute("user");
        PageInfo<Comment> pageInfo =commentService.getCommentListByPageAndUserId(pageIndex,pageSize,user.getUserId());
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("pageUrlPrefix","/admin/comment?pageIndex");
        return "Admin/Comment/index";
    }

}
