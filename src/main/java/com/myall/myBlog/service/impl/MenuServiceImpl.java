package com.myall.myBlog.service.impl;

import com.myall.myBlog.mapper.MenuMapper;
import com.myall.myBlog.pojo.Menu;
import com.myall.myBlog.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    MenuMapper menuMapper;

    @Override
    public List<Menu> getMenuList() {
        return menuMapper.getMenuList();
    }

    @Override
    public Menu insert(Menu menu) {
        menu.setMenuOrder(1);
        menuMapper.insert(menu);
        return menu;
    }

    @Override
    public void delete(Integer id) {
        menuMapper.deleteById(id);
    }

    @Override
    public void update(Menu menu) {
        menuMapper.update(menu);
    }

    @Override
    public Menu getMenuById(Integer id) {
        return menuMapper.getMenuById(id);
    }
}
