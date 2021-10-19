package com.myall.myBlog.controller.admin;

import com.myall.myBlog.pojo.Page;
import com.myall.myBlog.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/page")
public class BackPageController {
    @Autowired
    PageService pageService;

    @RequestMapping("")
    public String index(Model model){
        List<Page> pageList = pageService.getPageList(null);
        model.addAttribute("pageList",pageList);
        return "Admin/Page/index";
    }
    @RequestMapping("/insert")
    public String insert(){
        return "Admin/Page/insert";
    }
    @RequestMapping("/insertSubmit")
    public String insertSubmit(Page page){
        if(pageService.getPageByKey(null,page.getPageKey())==null){
            pageService.insert(page);
        }
        return "redirect:/admin/page";
    }
    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable("id")Integer id,Model model){
        Page page=pageService.getPageById(id);
        model.addAttribute("page",page);
        return "Admin/Page/edit";
    }
    @RequestMapping("/editSubmit")
    public String editSubmit(Page page){
        if(pageService.getPageByKey(null, page.getPageKey()).getPageKey().equals(page.getPageKey())){
            pageService.update(page);
        }
        return "redirect:/admin/page";
    }
    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable("id")Integer id){
        pageService.delete(id);
        return "redirect:/admin/page";
    }
}
