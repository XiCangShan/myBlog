package com.myall.myBlog.interceptor;

import com.myall.myBlog.enums.ArticleStatus;
import com.myall.myBlog.enums.LinkStatus;
import com.myall.myBlog.pojo.*;
import com.myall.myBlog.service.*;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Component
public class HomeResourceInterceptor implements HandlerInterceptor {
    @Autowired
    private OptionsService optionsService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private TagService tagService;
    @Autowired
    private LinkService linkService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
//        基本信息展示
        Options options = optionsService.getOptions();
        request.setAttribute("options", options);
//        菜单
        List<Menu> menuList = menuService.getMenuList();
        request.setAttribute("menuList", menuList);
//         分类
        List<Category> allCategoryList = categoryService.getCategoryList();
        request.setAttribute("allCategoryList", allCategoryList);
//          网站概况
        List<String> siteBasicStatistics = new ArrayList<>();
        siteBasicStatistics.add(articleService.countArticle(ArticleStatus.PUBLISH.getValue()) + "");
        siteBasicStatistics.add(articleService.countArticleComment() + "");
        siteBasicStatistics.add(categoryService.countCategory() + "");
        siteBasicStatistics.add(tagService.countTag() + "");
        siteBasicStatistics.add(linkService.countLink(LinkStatus.NORMAL.getValue()) + "");
        siteBasicStatistics.add(articleService.countArticleView() + "");
        request.setAttribute("siteBasicStatistics", siteBasicStatistics);
//最后更新文章
        Article lastUpdateArticle=articleService.getArticleByLastUpdate();
        request.setAttribute("lastUpdateArticle",lastUpdateArticle);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
