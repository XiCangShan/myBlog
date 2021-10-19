package com.myall.myBlog.controller.home;

import com.myall.myBlog.enums.PageStatus;
import com.myall.myBlog.pojo.Article;
import com.myall.myBlog.pojo.Category;
import com.myall.myBlog.pojo.Page;
import com.myall.myBlog.pojo.Tag;
import com.myall.myBlog.service.ArticleService;
import com.myall.myBlog.service.CategoryService;
import com.myall.myBlog.service.PageService;
import com.myall.myBlog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class PageController {
    @Autowired
    ArticleService articleService;
    @Autowired
    PageService pageService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    TagService tagService;
    @RequestMapping("/articleFile")
    public String articleFile(Model model){
        List<Article> articleList = articleService.getArticleListNotWithContent();
        model.addAttribute("articleList", articleList);
        List<Article> mostCommentArticleList = articleService.getCommentArticleList(10);
        model.addAttribute("mostCommentArticleList", mostCommentArticleList);
        return "Home/Page/articleFile";
    }
    @RequestMapping("/{key}")
    public String aboutSite(@PathVariable("key") String key,Model model){
        Page page=pageService.getPageByKey(PageStatus.NORMAL.getValue(),key);
        if (page==null){
            return "Home/Error/404";
        }
        model.addAttribute("page",page);
        List<Article> mostCommentArticleList = articleService.getCommentArticleList(10);
        model.addAttribute("mostCommentArticleList", mostCommentArticleList);
        return "Home/Page/page";
    }
    @RequestMapping(value = "/map")
    public String siteMap(Model model) {
        //文章显示
        List<Article> articleList = articleService.getArticleListNotWithContent();
        model.addAttribute("articleList", articleList);
        //分类显示
        List<Category> categoryList = categoryService.getCategoryList();
        model.addAttribute("categoryList", categoryList);
        //标签显示
        List<Tag> tagList = tagService.getTagList();
        model.addAttribute("tagList", tagList);

        //侧边栏显示
        //获得热评文章
        List<Article> mostCommentArticleList = articleService.getCommentArticleList(8);
        model.addAttribute("mostCommentArticleList", mostCommentArticleList);
        return "Home/Page/siteMap";
    }
    @RequestMapping(value = "/message")
    public String message(Model model) {

        //侧边栏显示
        //获得热评文章
        List<Article> mostCommentArticleList = articleService.getCommentArticleList(8);
        model.addAttribute("mostCommentArticleList", mostCommentArticleList);
        return "Home/Page/message";
    }
    @RequestMapping("/403")
    public String noAdmin(){
       return  "Home/Error/403";
    }
    @RequestMapping("/404")
    public String notFound(){
        return  "Home/Error/404";
    }
    @RequestMapping("/500")
    public String serverError(){
        return  "Home/Error/500";
    }
}
