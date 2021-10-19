package com.myall.myBlog.service;

import com.myall.myBlog.pojo.Category;

import java.util.List;

public interface CategoryService {
//    统计分类
    int countCategory();
//    得到分类列表
    List<Category> getCategoryList();
//    得到分类列表（封装articleCount）
    List<Category> getCategoryListWithCount();
//    删除分类
    void deleteCategoryById(Integer id);
//    获取分类
    Category geyCategoryById(Integer id);
    Category geyCategoryByName(String name);
//    添加分类
    Category insert(Category category);
//    更新分类
    void update(Category category);
}
