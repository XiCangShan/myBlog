package com.myall.myBlog.service.impl;

import com.myall.myBlog.enums.PageStatus;
import com.myall.myBlog.mapper.PageMapper;
import com.myall.myBlog.pojo.Page;
import com.myall.myBlog.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public class PageServiceImpl implements PageService {
    @Autowired
    PageMapper pageMapper;
    @Override
    public List<Page> getPageList(Integer status) {
        return pageMapper.getPageList(status);
    }

    @Override
    public Page getPageByKey(Integer status, String key) {
        return pageMapper.getPageListByKey(status, key);
    }

    @Override
    public Page getPageById(Integer id) {
        return pageMapper.getPageById(id);
    }

    @Override
    public void insert(Page page) {
        page.setPageCreateTime(new Date());
        page.setPageUpdateTime(new Date());
        page.setPageStatus(PageStatus.NORMAL.getValue());
        pageMapper.insert(page);
    }

    @Override
    public void update(Page page) {
        pageMapper.update(page);
    }

    @Override
    public void delete(Integer id) {
        pageMapper.deleteById(id);
    }
}
