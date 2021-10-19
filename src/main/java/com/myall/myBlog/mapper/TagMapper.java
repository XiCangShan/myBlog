package com.myall.myBlog.mapper;

import com.myall.myBlog.pojo.Tag;

import java.util.List;

public interface TagMapper {
    int deleteById(Integer id);
    int insert(Tag tag);
    Tag getTagById(Integer id);
    Tag getTagByName(String name);
    int update(Tag tag);
    int countTag();
    List<Tag> getTagList();

}
