package com.myall.myBlog.controller.admin;

import com.myall.myBlog.pojo.Category;
import com.myall.myBlog.service.ArticleService;
import com.myall.myBlog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/admin/category")
public class BackCategotyController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    ArticleService articleService;
    @RequestMapping("")
    public String index(Model model){
        List<Category> categoryList = categoryService.getCategoryListWithCount();
        model.addAttribute("categoryList",categoryList);
        return "Admin/Category/index";
    }
//    @RequestMapping("/insert")
//    public String insert(){
//        return "Admin/Category/index";
//    }
    @RequestMapping(value = "/insertSubmit",method = RequestMethod.POST)
    public String insertSubmit(Category category){
        categoryService.insert(category);
        return "redirect:/admin/category";
    }
    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable("id")Integer id){
        Integer count=articleService.countArticleByCategoryId(id);
        if(count==0){
            categoryService.deleteCategoryById(id);
        }
        return "redirect:/admin/category";
    }
    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable("id")Integer id,Model model){
        Category category = categoryService.geyCategoryById(id);
        model.addAttribute("category",category);
        List<Category> categoryList = categoryService.getCategoryListWithCount();
        model.addAttribute("categoryList",categoryList);
        return "Admin/Category/edit";
    }
    @RequestMapping(value = "/editSubmit",method = RequestMethod.POST)
    public String editSubmit(Category category){
        categoryService.update(category);
        return "redirect:/admin/category";
    }
}
