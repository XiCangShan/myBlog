package com.myall.myBlog.mapper;

import com.myall.myBlog.pojo.ArticleTagRef;
import com.myall.myBlog.pojo.Tag;

import java.util.List;

public interface ArticleTagRefMapper {
    int insert(ArticleTagRef articleTagRef);
    int deleteByTagId(Integer tagId);
    int deleteByArticleId(Integer tagId);
    int countArticleByTagId(Integer tagId);
    List<Tag> getTagListByArticleId(Integer articleId);
}
