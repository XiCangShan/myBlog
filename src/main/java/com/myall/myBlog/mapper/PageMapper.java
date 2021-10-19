package com.myall.myBlog.mapper;

import com.myall.myBlog.pojo.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PageMapper {
    int deleteById(Integer id);
    int insert(Page page);
    Page getPageById(Integer id);
    int update(Page page);
    List<Page> getPageList(@Param("status")Integer status);
    Page getPageListByKey(@Param("status") Integer status,@Param("key") String key);
}
