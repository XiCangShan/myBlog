package com.myall.myBlog.controller.home;

import com.github.pagehelper.PageInfo;
import com.myall.myBlog.enums.ArticleStatus;
import com.myall.myBlog.enums.LinkStatus;
import com.myall.myBlog.enums.NoticeStatus;
import com.myall.myBlog.pojo.*;
import com.myall.myBlog.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    ArticleService articleService;
    @Autowired
    TagService tagService;
    @Autowired
    CommentService commentService;
    @Autowired
    LinkService linkService;
    @Autowired
    NoticeService noticeService;
    @RequestMapping(value = {"/", "/article"})
    public String index(@RequestParam(defaultValue = "1") Integer pageIndex,
                       @RequestParam(defaultValue = "10") Integer pageSize,
                       Model model) {
//首页文章注入
        HashMap<String, Object> criteria = new HashMap<>(1);
        criteria.put("status", ArticleStatus.PUBLISH.getValue());
        PageInfo<Article> pageInfo = articleService.pageArticle(pageIndex, pageSize, criteria);
        model.addAttribute("pageInfo",pageInfo);
//首页展示标签
        List<Tag> allTagList = tagService.getTagList();
        model.addAttribute("allTagList", allTagList);
//首页最新评论
        List<Comment> recentCommentList = commentService.getNewCommentListByUserId(null, 10);
        model.addAttribute("recentCommentList", recentCommentList);
//首页友情链接
        List<Link> linkList=linkService.getLinkList(LinkStatus.NORMAL.getValue());
        model.addAttribute("linkList",linkList);
//首页公告
        List<Notice> noticeList=noticeService.getNoticeList(NoticeStatus.NORMAL.getValue());
        model.addAttribute("noticeList",noticeList);
        model.addAttribute("pageUrlPrefix","/article?pageIndex");
        return "Home/index";
    }

    @RequestMapping("/search")
    public String search(
            @RequestParam("keywords")String keywords,
            @RequestParam(defaultValue = "1")Integer pageIndex,
            @RequestParam(defaultValue = "10")Integer pageSize,
            Model model){
        HashMap<String,Object> criteria=new HashMap<>(2);
        criteria.put("status",ArticleStatus.PUBLISH.getValue());
        criteria.put("keywords",keywords);
        PageInfo<Article> pageInfo=articleService.pageArticle(pageIndex,pageSize,criteria);
        model.addAttribute("pageInfo",pageInfo);

        List<Tag> allTagList = tagService.getTagList();
        model.addAttribute("allTagList", allTagList);
        //获得随机文章
        List<Article> randomArticleList = articleService.getRandomArticleList(8);
        model.addAttribute("randomArticleList", randomArticleList);
        //获得热评文章
        List<Article> mostCommentArticleList = articleService.getCommentArticleList(8);
        model.addAttribute("mostCommentArticleList", mostCommentArticleList);
        //最新评论
        List<Comment> recentCommentList = commentService.getNewCommentListByUserId(null, 10);
        model.addAttribute("recentCommentList", recentCommentList);

        model.addAttribute("pageUrlPrefix","/search?keywords="+keywords+"&pageIndex");
        return "Home/Page/search";
    }
}
