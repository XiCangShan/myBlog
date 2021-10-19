package com.myall.myBlog.service;

import com.myall.myBlog.pojo.Menu;

import java.util.List;

public interface MenuService {
    List<Menu> getMenuList();
    Menu insert(Menu menu);
    void delete(Integer id);
    void update(Menu menu);
    Menu getMenuById(Integer id);
}
