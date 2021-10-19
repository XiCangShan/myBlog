package com.myall.myBlog.service.impl;

import com.myall.myBlog.enums.NoticeStatus;
import com.myall.myBlog.mapper.NoticeMapper;
import com.myall.myBlog.pojo.Notice;
import com.myall.myBlog.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public class NoticeServiceImpl implements NoticeService {
    @Autowired
    NoticeMapper noticeMapper;

    @Override
    public void insert(Notice notice) {
        notice.setNoticeCreateTime(new Date());
        notice.setNoticeUpdateTime(new Date());
        notice.setNoticeStatus(NoticeStatus.NORMAL.getValue());
        notice.setNoticeOrder(1);
        noticeMapper.insert(notice);
    }

    @Override
    public void delete(Integer id) {
        noticeMapper.deleteById(id);
    }

    @Override
    public void update(Notice notice) {
        notice.setNoticeUpdateTime(new Date());
        noticeMapper.update(notice);
    }

    @Override
    public Notice getNoticeById(Integer id) {
        return noticeMapper.getNoticeById(id);
    }

    @Override
    public List<Notice> getNoticeList(Integer status) {
        return noticeMapper.getNoticeList(status);
    }
}
