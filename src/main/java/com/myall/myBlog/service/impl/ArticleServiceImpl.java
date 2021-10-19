package com.myall.myBlog.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.myall.myBlog.enums.ArticleCommentStatus;
import com.myall.myBlog.mapper.*;
import com.myall.myBlog.pojo.*;
import com.myall.myBlog.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
@Slf4j
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    ArticleMapper articleMapper;
    @Autowired
    ArticleCategoryRefMapper articleCategoryRefMapper;
    @Autowired
    ArticleTagRefMapper articleTagRefMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    CommentMapper commentMapper;

    @Override
    public Integer countArticle(Integer status) {
        int count = 0;
        try {
            count = articleMapper.countArticleByStatus(status);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("根据状态统计文章数,status:{},catch:{}", status, e);
        }
        return count;
    }

    @Override
    public Integer countArticleComment() {
        int count = 0;
        try {
            count = articleMapper.countArticleComment();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("统计评论失败，catch:{}", e);
        }
        return count;
    }

    @Override
    public Integer countArticleView() {
        int count = 0;
        try {
            count = articleMapper.countArticleView();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("统计点击失败，catch:{}", e);
        }
        return count;
    }

    @Override
    public Integer countArticleByCategoryId(Integer id) {
        int count = 0;
        try {
            count = articleCategoryRefMapper.countArticleByCategoryId(id);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("统计评论失败，CategoryId:{}，catch:{}", id, e);
        }
        return count;
    }

    @Override
    public Integer countArticleByTagId(Integer id) {
        return articleTagRefMapper.countArticleByTagId(id);
    }

    @Override
    public List<Article> getArticleList(HashMap<String, Object> criteria) {
        return articleMapper.getArticleListByMap(criteria);
    }

    @Override
    public List<Article> getNewArticleListByUserId(Integer userId, Integer limit) {
        return articleMapper.getArticleByLimit(userId, limit);
    }

    @Override
    public List<Article> getViewArticleList(Integer limit) {
        return articleMapper.getArticleByViewCount(limit);
    }

    @Override
    public List<Article> getCommentArticleList(Integer limit) {
        return articleMapper.getArticleByCommentCount(limit);
    }

    @Override
    public List<Article> getRandomArticleList(Integer limit) {
        return articleMapper.getRandomArticle(limit);
    }

    @Override
    public List<Article> getArticleListByCategoryId(Integer categoryId, Integer limit) {
        return articleMapper.getArticleByCategoryId(categoryId, limit);
    }

    @Override
    public List<Article> getArticleListByCategoryIds(List<Integer> categoryIds, Integer limit) {
        if (categoryIds == null ||categoryIds.size() == 0  ) {
            return null;
        }
        return articleMapper.getArticleByCategoryIds(categoryIds, limit);
    }

    @Override
    public List<Integer> getCategoryIdListByArticleId(Integer articleId) {
        return articleCategoryRefMapper.getCategoryIdListByArticleId(articleId);
    }

    @Override
    public List<Article> getArticleListNotWithContent() {
        return articleMapper.getArticleListByAllNotWithContent();
    }

    @Override
    public void updateArticle(Article article) {
        articleMapper.update(article);
    }

    @Override
    public void updateArticleDetail(Article article) {
        article.setArticleUpdateTime(new Date());
        articleMapper.update(article);
        List<Tag> tags = article.getTagList();
        List<Category> categories = article.getCategoryList();

        if (tags != null) {
            articleTagRefMapper.deleteByArticleId(article.getArticleId());
            for (int i = 0; i < tags.size(); i++) {
                ArticleTagRef articleTagRef = new ArticleTagRef(article.getArticleId(), tags.get(i).getTagId());
                articleTagRefMapper.insert(articleTagRef);
            }
        }
        if (categories != null) {
            articleCategoryRefMapper.deleteByArticleId(article.getArticleId());
            for (int i = 0; i < categories.size(); i++) {
                ArticleCategoryRef articleCategoryRef = new ArticleCategoryRef(article.getArticleId(), categories.get(i).getCategoryId());
                articleCategoryRefMapper.insert(articleCategoryRef);
            }
        }
    }

    @Override
    public void updateCommentCount(Integer articleId) {
        articleMapper.updateCommentCountByArticleId(articleId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Integer id) {
        articleMapper.deleteByArticleId(id);
        articleCategoryRefMapper.deleteByArticleId(id);
        articleTagRefMapper.deleteByArticleId(id);
        commentMapper.deleteByArticleId(id);
    }

    @Override
    public void deleteBatch(List<Integer> ids) {
        articleMapper.deleteBatch(ids);
    }

    @Override
    public PageInfo<Article> pageArticle(Integer pageIndex, Integer pageSize, HashMap<String, Object> criteria) {
        PageHelper.startPage(pageIndex, pageSize);
        List<Article> articleList = articleMapper.getArticleListByMap(criteria);
//        给每个article封装categoryList
        for (int i = 0; i < articleList.size(); i++) {
            List<Category> categoryList = articleCategoryRefMapper.getCategoryListByArticleId(articleList.get(i).getArticleId());
            if (categoryList == null || categoryList.size() == 0) {
                categoryList = new ArrayList<>();
                categoryList.add(new Category().Default());
            }
            articleList.get(i).setCategoryList(categoryList);
            articleList.get(i).setUser(userMapper.getUserById(articleList.get(i).getArticleUserId()));
        }
        return new PageInfo<>(articleList);
    }

    @Override
    public Article getArticleByIdAndStatus(Integer id, Integer status) {
        Article article = articleMapper.getArticleByStatusAndId(status, id);
        if(article!=null){
            List<Category> categoryList = articleCategoryRefMapper.getCategoryListByArticleId(id);
            List<Tag> tagList = articleTagRefMapper.getTagListByArticleId(id);
            article.setCategoryList(categoryList);
            article.setTagList(tagList);
        }
        return article;
    }

    @Override
    public Article getArticleByAfter(Integer id) {
        return articleMapper.getArticleByAfterId(id);
    }

    @Override
    public Article getArticleByBefore(Integer id) {
        return articleMapper.getArticleByBeforeId(id);
    }

    @Override
    public Article getArticleByLastUpdate() {
        return articleMapper.getLastUpdateArticle();
    }

    @Override
    public void insert(Article article) {
        article.setArticleCreateTime(new Date());
        article.setArticleUpdateTime(new Date());
        article.setArticleCommentCount(0);
        article.setArticleViewCount(0);
        article.setArticleLikeCount(0);
        article.setArticleIsComment(ArticleCommentStatus.ALLOW.getCode());
        article.setArticleOrder(1);
        if(StringUtils.isEmpty(article.getArticleThumbnail())){
            String random="/img/thumbnail/random/img_"+ RandomUtil.randomNumbers(1) +".jpg";
            article.setArticleThumbnail(random);
        }
        articleMapper.insert(article);
//        创建关联表
        List<Category> categoryList = article.getCategoryList();
        List<Tag> tagList = article.getTagList();
        for (int i = 0; i < categoryList.size(); i++) {
            ArticleCategoryRef articleCategoryRef = new ArticleCategoryRef(article.getArticleId(), categoryList.get(i).getCategoryId());
            articleCategoryRefMapper.insert(articleCategoryRef);
        }
        for (int i = 0; i < tagList.size(); i++) {
            ArticleTagRef articleTagRef=new ArticleTagRef(article.getArticleId(),tagList.get(i).getTagId());
            articleTagRefMapper.insert(articleTagRef);
        }
    }
}
