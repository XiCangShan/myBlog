package com.myall.myBlog.service;

import com.myall.myBlog.pojo.Tag;

import java.util.List;

public interface TagService {
    Integer countTag();
    List<Tag> getTagList();
    List<Tag> getTagListByArticleId(Integer atricleId);
    List<Tag> getTagListWithCount();
    Tag getTagById(Integer id);
    Tag getTagByName(String name);
    void insert(Tag tag);
    void delete(Integer id);
    void update(Tag tag);
}
