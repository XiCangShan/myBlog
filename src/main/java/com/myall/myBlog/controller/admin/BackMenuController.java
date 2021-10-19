package com.myall.myBlog.controller.admin;

import com.myall.myBlog.pojo.Menu;
import com.myall.myBlog.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/admin/menu")
public class BackMenuController {
    @Autowired
    MenuService menuService;

    @RequestMapping("")
    public String index(Model model) {
        List<Menu> menuList = menuService.getMenuList();
        model.addAttribute("menuList", menuList);
        return "Admin/Menu/index";
    }

    @RequestMapping(value = "/insertSubmit", method = RequestMethod.POST)
    public String insertSubmit(Menu menu) {
        menuService.insert(menu);
        return "redirect:/admin/menu";
    }

    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        Menu menu = menuService.getMenuById(id);
        model.addAttribute("menu", menu);
        return "Admin/Menu/edit";
    }
    @RequestMapping("/editSubmit")
    public String editSubmit(Menu menu){
        menuService.update(menu);
        return "redirect:/admin/menu";
    }
    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        menuService.delete(id);
        return "redirect:/admin/menu";
    }
}
