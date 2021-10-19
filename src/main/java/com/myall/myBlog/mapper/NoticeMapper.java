package com.myall.myBlog.mapper;

import com.myall.myBlog.pojo.Notice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NoticeMapper {
    int deleteById(Integer id);
    int insert(Notice notice);
    Notice getNoticeById(Integer id);
    int update(Notice notice);
    int countNotice(@Param(value = "status")Integer status);
    List<Notice> getNoticeList(@Param(value = "status")Integer status);
}
