package com.myall.myBlog.service;

import com.myall.myBlog.pojo.Link;

import java.util.List;

public interface LinkService {
//    统计link
    int countLink(Integer status);
//    根据id获取link
    Link getLinkById(Integer id);
//    获取link列表
    List<Link> getLinkList(Integer status);
    void insertLink(Link link);
    void delete(Integer id);
    void update(Link link);
}
