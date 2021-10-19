package com.myall.myBlog.controller.home;

import com.github.pagehelper.PageInfo;
import com.myall.myBlog.enums.ArticleStatus;
import com.myall.myBlog.pojo.Article;
import com.myall.myBlog.pojo.Category;
import com.myall.myBlog.pojo.Tag;
import com.myall.myBlog.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;

@Controller
public class CategoryController {
    @Autowired
    ArticleService articleService;
    @Autowired
    TagService tagService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    LinkService linkService;
    @Autowired
    NoticeService noticeService;
    @RequestMapping("/category/{id}")
    public String category(@PathVariable("id") Integer id,
                           @RequestParam(defaultValue = "1") Integer pageIndex,
                           @RequestParam(defaultValue = "10") Integer pageSize,
                           Model model){
// 分类和文章
        Category category=categoryService.geyCategoryById(id);
        if (category==null){
            return "Home/Error/404";
        }
        model.addAttribute("category",category);

        HashMap<String,Object> criteria=new HashMap<>(2);
        criteria.put("categoryId",id);
        criteria.put("status", ArticleStatus.PUBLISH.getValue());
        PageInfo<Article> articleListByCategory=articleService.pageArticle(pageIndex,pageSize,criteria);
        model.addAttribute("pageInfo",articleListByCategory);
//右边栏
        List<Article> articleList=articleService.getCommentArticleList(10);
        model.addAttribute("mostCommentArticleList",articleList);
        List<Tag> allTagList = tagService.getTagList();
        model.addAttribute("allTagList", allTagList);
        List<Article> articleRList=articleService.getRandomArticleList(10);
        model.addAttribute("randomArticleList",articleRList);
//分页请求前缀
        model.addAttribute("pageUrlPrefix",id+"?pageIndex");
        return "Home/Page/articleListByCategory";
    }
}
