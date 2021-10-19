package com.myall.myBlog.controller.admin;

import com.myall.myBlog.enums.ArticleStatus;
import com.myall.myBlog.enums.UserRole;
import com.myall.myBlog.pojo.Tag;
import com.myall.myBlog.pojo.User;
import com.myall.myBlog.service.ArticleService;
import com.myall.myBlog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin/tag")
public class BackTagController {
    @Autowired
    TagService tagService;
    @Autowired
    ArticleService articleService;
    @RequestMapping("")
    public String index(Model model){
        List<Tag> tagList = tagService.getTagListWithCount();
        model.addAttribute("tagList",tagList);
        return "Admin/Tag/index";
    }
    @RequestMapping(value = "/delete/{id}")
    public String showTag(@PathVariable("id")Integer id, HttpSession session){
        User user=(User) session.getAttribute("user");
        if(!user.getUserRole().equals(UserRole.ADMIN.getValue())){
            return "redirect:/403";
        }
        Integer count=articleService.countArticleByTagId(id);
        if(count==0){
            tagService.delete(id);
        }
        return "redirect:/admin/tag";
    }
    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable("id")Integer id,Model model,HttpSession session){
        User user=(User) session.getAttribute("user");
        if(!user.getUserRole().equals(UserRole.ADMIN.getValue())){
            return "redirect:/403";
        }
        Tag tag=tagService.getTagById(id);
        model.addAttribute("tag",tag);
        return "Admin/Tag/edit";
    }
    @RequestMapping("/insertSubmit")
    public String insertSubmit(Tag tag,HttpSession session){
        User user=(User) session.getAttribute("user");
        if(!user.getUserRole().equals(UserRole.ADMIN.getValue())){
            return "redirect:/403";
        }
        tagService.insert(tag);
        return "redirect:/admin/tag";
    }
    @RequestMapping("/editSubmit")
    public String editSubmit(Tag tag,HttpSession session){
        User user=(User) session.getAttribute("user");
        if(!user.getUserRole().equals(UserRole.ADMIN.getValue())){
            return "redirect:/403";
        }
        tagService.update(tag);
        return "redirect:/admin/tag";
    }
}
