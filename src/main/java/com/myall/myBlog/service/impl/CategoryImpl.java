package com.myall.myBlog.service.impl;

import com.myall.myBlog.mapper.ArticleCategoryRefMapper;
import com.myall.myBlog.mapper.CategoryMapper;
import com.myall.myBlog.pojo.Category;
import com.myall.myBlog.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Slf4j
public class CategoryImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private ArticleCategoryRefMapper articleCategoryRefMapper;
    @Override
    public int countCategory() {
        int count=0;
        try{
            count = categoryMapper.countCategory();
        }catch (Exception e){
            e.printStackTrace();
            log.error("统计分类，catch:{}",e);
        }
        return count;
    }

    @Override
    public List<Category> getCategoryList() {
        List<Category> categoryList =null;
        try{
            categoryList = categoryMapper.getCategoryList();
        }catch (Exception e){
            e.printStackTrace();
            log.error("获取分类列表失败，catch:{}",e);
        }
        return categoryList;
    }

    @Override
    public List<Category> getCategoryListWithCount() {
        List<Category> categoryList =null;
        try{
            categoryList = categoryMapper.getCategoryList();
            for (int i = 0; i <categoryList.size(); i++) {
                int articleCount=articleCategoryRefMapper.countArticleByCategoryId(categoryList.get(i).getCategoryId());
                categoryList.get(i).setArticleCount(articleCount);
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error("获取分类列表失败，catch:{}",e);
        }
        return categoryList;
    }

    @Override
    public void deleteCategoryById(Integer id) {
        try{
           categoryMapper.deleteCategoryById(id);
            articleCategoryRefMapper.deleteByCategoryId(id);
        }catch (Exception e){
            e.printStackTrace();
            log.error("删除分类失败，id:{},catch:{}",id,e);
        }
    }

    @Override
    public Category geyCategoryById(Integer id) {
        Category category=null;
        try{
            category=categoryMapper.getCategoryById(id);
        }catch (Exception e){
            e.printStackTrace();
            log.error("获取分类失败，id:{}，catch:{}",id,e);
        }
        return category;
    }

    @Override
    public Category geyCategoryByName(String name) {
        Category category=null;
        try{
            category=categoryMapper.getCategoryByCategoryName(name);
        }catch (Exception e){
            e.printStackTrace();
            log.error("获取分类失败，name,catch:{}",name,e);
        }
        return category;
    }

    @Override
    public Category insert(Category category) {
        try{
            categoryMapper.insert(category);
        }catch (Exception e){
            e.printStackTrace();
            log.error("增加分类失败，category:{},catch:{}",category,e);
        }
        return category;
    }

    @Override
    public void update(Category category) {
        try{
            categoryMapper.update(category);
        }catch (Exception e){
            e.printStackTrace();
            log.error("更新分类失败，category:{},catch:{}",category,e);
        }
    }
}
