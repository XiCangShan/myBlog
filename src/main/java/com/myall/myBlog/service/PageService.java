package com.myall.myBlog.service;

import com.myall.myBlog.pojo.Page;
import org.springframework.stereotype.Service;

import java.util.List;

public interface PageService {
    List<Page> getPageList(Integer status);
//    根据关键词获取pageList
    Page getPageByKey(Integer status,String key);
    Page getPageById(Integer id);
    void insert(Page page);
    void update(Page page);
    void delete(Integer id);
}
