package com.myall.myBlog.controller.home;

import com.myall.myBlog.enums.LinkStatus;
import com.myall.myBlog.pojo.Article;
import com.myall.myBlog.pojo.Link;
import com.myall.myBlog.service.ArticleService;
import com.myall.myBlog.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class LinkController {
    @Autowired
    LinkService linkService;
    @Autowired
    ArticleService articleService;

    @RequestMapping("/applyLink")
    public String toApplyLinkPage(Model model){
        List<Article> articleList=articleService.getCommentArticleList(8);
        model.addAttribute("mostCommentArticleList",articleList);
        return "Home/Page/applyLink";
    }
    @RequestMapping("/applyLinkSubmit")
    @ResponseBody
    public void applyLinkSubmit(Link link){
        linkService.insertLink(link);
    }

}
