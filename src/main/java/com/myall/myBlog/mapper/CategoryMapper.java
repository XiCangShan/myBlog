package com.myall.myBlog.mapper;

import com.myall.myBlog.pojo.Category;

import java.util.List;

public interface CategoryMapper {
    int insert(Category category);
    int update(Category category);

//    获取全部分类list
    List<Category> getCategoryList();
//    根据id获取分类
    Category getCategoryById(Integer id);
    int deleteCategoryById(Integer id);
//    统计分类总数
    int countCategory();
//    根据父分类找子分类
    List<Category> getCategoryListByPid(Integer pid);
//    根据分类名获取标签
    Category getCategoryByCategoryName(String categoryName);
}
