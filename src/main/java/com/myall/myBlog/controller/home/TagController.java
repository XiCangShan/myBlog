package com.myall.myBlog.controller.home;

import com.github.pagehelper.PageInfo;
import com.myall.myBlog.enums.ArticleStatus;
import com.myall.myBlog.pojo.Article;
import com.myall.myBlog.pojo.Tag;
import com.myall.myBlog.service.ArticleService;
import com.myall.myBlog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;

@Controller
public class TagController {
    @Autowired
    ArticleService articleService;
    @Autowired
    TagService tagService;
    @RequestMapping("/tag/{tagId}")
    public String tag(@PathVariable("tagId")Integer tagId,
                      @RequestParam(defaultValue = "1")Integer pageIndex,
                      @RequestParam(defaultValue = "10")Integer pageSize,
                      Model model){
        Tag tag = tagService.getTagById(tagId);
        if (tag == null) {
            return "redirect:/404";
        }
        model.addAttribute("tag", tag);

        //文章列表
        HashMap<String, Object> criteria = new HashMap<>(2);
        criteria.put("tagId", tagId);
        criteria.put("status", ArticleStatus.PUBLISH.getValue());
        PageInfo<Article> articlePageInfo = articleService.pageArticle(pageIndex, pageSize, criteria);
        model.addAttribute("pageInfo", articlePageInfo);

        //侧边栏
        //标签列表显示
        List<Tag> allTagList = tagService.getTagList();
        model.addAttribute("allTagList", allTagList);
        //获得随机文章
        List<Article> randomArticleList = articleService.getRandomArticleList(8);
        model.addAttribute("randomArticleList", randomArticleList);
        //获得热评文章
        List<Article> mostCommentArticleList = articleService.getCommentArticleList(8);
        model.addAttribute("mostCommentArticleList", mostCommentArticleList);
        model.addAttribute("pageUrlPrefix", "/tag/"+tagId+"?pageIndex");

        return "Home/Page/articleListByTag";
    }
}
