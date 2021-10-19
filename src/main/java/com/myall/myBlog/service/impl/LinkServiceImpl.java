package com.myall.myBlog.service.impl;

import com.myall.myBlog.enums.LinkStatus;
import com.myall.myBlog.mapper.LinkMapper;
import com.myall.myBlog.pojo.Link;
import com.myall.myBlog.service.LinkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class LinkServiceImpl implements LinkService {
    @Autowired
    LinkMapper linkMapper;
    @Override
    public int countLink(Integer status) {
        return linkMapper.countLink(status);
    }

    @Override
    public Link getLinkById(Integer id) {
        return linkMapper.getLinkById(id);
    }

    @Override
    public List<Link> getLinkList(Integer status) {
        return linkMapper.getLinkList(status);
    }

    @Override
    public void delete(Integer id) {
        linkMapper.deleteById(id);
    }

    @Override
    public void update(Link link) {
        linkMapper.update(link);
    }

    @Override
    public void insertLink(Link link) {
        link.setLinkCreateTime(new Date());
        link.setLinkUpdateTime(new Date());
        link.setLinkStatus(LinkStatus.NORMAL.getValue());
        linkMapper.insert(link);
    }
}
