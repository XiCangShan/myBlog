package com.myall.myBlog.mapper;

import com.myall.myBlog.pojo.Menu;

import java.util.List;

public interface MenuMapper {
    int deleteById(Integer id);
    int insert(Menu menu);
    Menu getMenuById(Integer id);
    int update(Menu menu);
    List<Menu> getMenuList();
}
