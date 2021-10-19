package com.myall.myBlog.mapper;

import com.myall.myBlog.pojo.Link;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LinkMapper {
    int deleteById(Integer id);
    Link getLinkById(Integer id);
    int insert(Link link);
    int update(Link link);
    int countLink(@Param(value = "status")Integer status);
    List<Link> getLinkList(@Param(value = "status")Integer status);
}
