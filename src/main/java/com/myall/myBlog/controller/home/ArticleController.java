package com.myall.myBlog.controller.home;

import com.alibaba.fastjson.JSON;
import com.myall.myBlog.enums.ArticleStatus;
import com.myall.myBlog.pojo.Article;
import com.myall.myBlog.pojo.Comment;
import com.myall.myBlog.pojo.Tag;
import com.myall.myBlog.pojo.User;
import com.myall.myBlog.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ArticleController {
    @Autowired
    ArticleService articleService;
    @Autowired
    TagService tagService;
    @Autowired
    CommentService commentService;
    @Autowired
    UserService userService;
    @RequestMapping("/article/{id}")
    public String article(@PathVariable("id") Integer id, Model model, HttpSession session){

        Article article=articleService.getArticleByIdAndStatus(id,ArticleStatus.PUBLISH.getValue());
        if(article==null){
            return "redirect:/404";
        }
        User user=userService.getUserById(article.getArticleUserId());
        article.setUser(user);
        model.addAttribute("article",article);
        Article preArticle=articleService.getArticleByBefore(id);
        model.addAttribute("preArticle",preArticle);
        Article afterArticle=articleService.getArticleByAfter(id);
        model.addAttribute("afterArticle",afterArticle);

        List<Article> mostViewArticleList=articleService.getViewArticleList(5);
        model.addAttribute("mostViewArticleList",mostViewArticleList);

        List<Article> articleCommentList=articleService.getCommentArticleList(5);
        model.addAttribute("mostCommentArticleList",articleCommentList);

        List<Article> similarArticleList=articleService.getArticleListByCategoryId(article.getCategoryList().get(0).getCategoryId(),5);
        model.addAttribute("similarArticleList",similarArticleList);
        List<Comment> commentList=commentService.getCommentListByArticleId(id);
        model.addAttribute("commentList",commentList);

        List<Tag> allTagList = tagService.getTagList();
        model.addAttribute("allTagList", allTagList);
        List<Article> articleRList=articleService.getRandomArticleList(10);
        model.addAttribute("randomArticleList",articleRList);
        return "Home/Page/articleDetail";
    }

    @RequestMapping(value = "/article/like/{id}",method = RequestMethod.POST, produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String increaseLikeCount(@PathVariable("id") Integer id){
        Article article=articleService.getArticleByIdAndStatus(id, ArticleStatus.PUBLISH.getValue());
        Integer count = article.getArticleLikeCount()+1;
        article.setArticleLikeCount(count);
        articleService.updateArticle(article);
        return JSON.toJSONString(count);
    }
    @RequestMapping(value = "/article/view/{id}",method = RequestMethod.POST, produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String increaseViewCount(@PathVariable("id") Integer id){
        Article article=articleService.getArticleByIdAndStatus(id, ArticleStatus.PUBLISH.getValue());
        Integer count = article.getArticleViewCount()+1;
        article.setArticleViewCount(count);
        articleService.updateArticle(article);
        return JSON.toJSONString(count);
    }
}
