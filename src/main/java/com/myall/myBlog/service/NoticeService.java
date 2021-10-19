package com.myall.myBlog.service;

import com.myall.myBlog.pojo.Notice;

import java.util.List;

public interface NoticeService {
    void insert(Notice notice);
    void delete(Integer id);
    void update(Notice notice);
    Notice getNoticeById(Integer id);
    List<Notice> getNoticeList(Integer status);
}
