package com.myall.myBlog.controller.admin;

import cn.hutool.http.HtmlUtil;
import com.github.pagehelper.PageInfo;
import com.myall.myBlog.dto.ArticleParam;
import com.myall.myBlog.enums.UserRole;
import com.myall.myBlog.pojo.Article;
import com.myall.myBlog.pojo.Category;
import com.myall.myBlog.pojo.Tag;
import com.myall.myBlog.pojo.User;
import com.myall.myBlog.service.ArticleService;
import com.myall.myBlog.service.CategoryService;
import com.myall.myBlog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/admin/article")
public class BackArticleController {
    @Autowired
    ArticleService articleService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    TagService tagService;

    @RequestMapping("")
    public String articlePage(Model model,
                              HttpSession session,
                              @RequestParam(defaultValue = "1") Integer pageIndex,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(required = false) String status) {
        User user = (User) session.getAttribute("user");
        HashMap<String, Object> criteria = new HashMap<>(1);
//        翻页地址的拼接
        if (status == null) {
            model.addAttribute("pageUrlPrefix", "/admin/article?pageIndex");
        } else {
            criteria.put("status", status);
            model.addAttribute("pageUrlPrefix", "/admin/article?status=" + status + "&pageIndex");
        }
//        不是管理员就查所有的
        if (!user.getUserRole().equals(UserRole.ADMIN.getValue())) {
            criteria.put("userId", user.getUserId());
        }

        PageInfo<Article> pageInfo = articleService.pageArticle(pageIndex, pageSize, criteria);
        model.addAttribute("pageInfo", pageInfo);
        return "Admin/Article/index";
    }

    @RequestMapping("/insert")
    public String insertArticle(Model model) {
        List<Category> categoryList = categoryService.getCategoryList();
        List<Tag> tagList = tagService.getTagList();
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("tagList", tagList);
        return "Admin/Article/insert";
    }

    @RequestMapping("/edit/{id}")
    public String editArticle(Model model, @PathVariable("id") Integer id, HttpSession session) {
        Article article = articleService.getArticleByIdAndStatus(id, null);
        User user = (User) session.getAttribute("user");
//        文章不存在
        if (article == null) {
            return "redirect:/404";
        }
//        越权操作
        if (!user.getUserRole().equals(UserRole.ADMIN.getValue()) && !article.getArticleUserId().equals(user.getUserId())) {
            return "redirect:/403";
        }
        model.addAttribute("article", article);
        List<Category> categoryList = categoryService.getCategoryList();
        List<Tag> tagList = tagService.getTagList();
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("tagList", tagList);

        return "Admin/Article/edit";
    }
    @RequestMapping(value = "/insertDraftSubmit",method = RequestMethod.POST)
    public String insertDraftSubmit(HttpSession session, ArticleParam articleParam){
        Article article = new Article();
        User user = (User) session.getAttribute("user");

        article.setArticleUserId(user.getUserId());
        article.setArticleTitle(articleParam.getArticleTitle());
        article.setArticleContent(articleParam.getArticleContent());

//提取摘要
        Integer summaryLength = 150;
        String summary = HtmlUtil.cleanHtmlTag(article.getArticleContent());
        if (summaryLength < summary.length()) {
            article.setArticleSummary(summary.substring(0, summaryLength));
        } else {
            article.setArticleSummary(article.getArticleContent());
        }
        List<Category> categoryList=new ArrayList<>();
        List<Tag> tagList=new ArrayList<>();
        article.setCategoryList(categoryList);
        article.setTagList(tagList);
        article.setArticleStatus(articleParam.getArticleStatus());
        articleService.insert(article);
        return "redirect:/admin";
    }


    @RequestMapping(value = "/insertSubmit", method = RequestMethod.POST)
    public String insertSubmit(HttpSession session, ArticleParam articleParam) {
        Article article = new Article();
        User user = (User) session.getAttribute("user");

        article.setArticleUserId(user.getUserId());
        article.setArticleTitle(articleParam.getArticleTitle());
        article.setArticleContent(articleParam.getArticleContent());
        article.setArticleOrder(articleParam.getArticleOrder());
        article.setArticleThumbnail(articleParam.getArticleThumbnail());
//提取摘要
        Integer summaryLength = 150;
        String summary = HtmlUtil.cleanHtmlTag(article.getArticleContent());
        if (summaryLength < summary.length()) {
            article.setArticleSummary(summary.substring(0, summaryLength));
        } else {
            article.setArticleSummary(article.getArticleContent());
        }

        List<Tag> tagList = new ArrayList<>();
        if (articleParam.getArticleTagIds() != null) {
            for (Integer tagId:articleParam.getArticleTagIds()) {
                tagList.add(new Tag(tagId));
            }
            article.setTagList(tagList);
        }
        List<Category> categoryList=new ArrayList<>();
        if(articleParam.getArticleChildCategoryId()!=null){
            categoryList.add(new Category(articleParam.getArticleChildCategoryId()));
        }
        if(articleParam.getArticleParentCategoryId()!=null){
            categoryList.add(new Category(articleParam.getArticleParentCategoryId()));
        }
        article.setCategoryList(categoryList);
        article.setArticleStatus(articleParam.getArticleStatus());
        articleService.insert(article);
        return "redirect:/admin/article";
    }
    @RequestMapping(value = "/editSubmit",method = RequestMethod.POST)
    public String editSubmit(HttpSession session, ArticleParam articleParam){
        Article article = new Article();
        User user = (User) session.getAttribute("user");
        article.setArticleId(articleParam.getArticleId());
        article.setArticleUserId(user.getUserId());
        article.setArticleTitle(articleParam.getArticleTitle());
        article.setArticleContent(articleParam.getArticleContent());
        article.setArticleOrder(articleParam.getArticleOrder());
        article.setArticleThumbnail(articleParam.getArticleThumbnail());
//提取摘要
        Integer summaryLength = 150;
        String summary = HtmlUtil.cleanHtmlTag(article.getArticleContent());
        if (summaryLength < summary.length()) {
            article.setArticleSummary(summary.substring(0, summaryLength));
        } else {
            article.setArticleSummary(article.getArticleContent());
        }

        List<Tag> tagList = new ArrayList<>();
        if (articleParam.getArticleTagIds() != null) {
            for (Integer tagId:articleParam.getArticleTagIds()) {
                tagList.add(new Tag(tagId));
            }
            article.setTagList(tagList);
        }
        List<Category> categoryList=new ArrayList<>();
        if(articleParam.getArticleChildCategoryId()!=null){
            categoryList.add(new Category(articleParam.getArticleChildCategoryId()));
        }
        if(articleParam.getArticleParentCategoryId()!=null){
            categoryList.add(new Category(articleParam.getArticleParentCategoryId()));
        }
        article.setCategoryList(categoryList);
        article.setArticleStatus(articleParam.getArticleStatus());
        articleService.updateArticleDetail(article);
        return "redirect:/admin/article";
    }

    @RequestMapping("/delete/{id}")
    public void deleteArticle(@PathVariable("id")Integer id,HttpSession session){
        Article dbArticle = articleService.getArticleByIdAndStatus(id,null);
        if (dbArticle == null) {
            return;
        }
        User user = (User) session.getAttribute("user");
        if (!Objects.equals(dbArticle.getArticleUserId(), user.getUserId()) && !Objects.equals(user.getUserRole(), UserRole.ADMIN.getValue())) {
            return;
        }
        articleService.delete(id);
    }
}
