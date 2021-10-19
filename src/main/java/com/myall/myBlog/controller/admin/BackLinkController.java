package com.myall.myBlog.controller.admin;

import com.myall.myBlog.enums.LinkStatus;
import com.myall.myBlog.pojo.Link;
import com.myall.myBlog.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/link")
public class BackLinkController {
    @Autowired
    LinkService linkService;

    @RequestMapping("")
    public String index(Model model){
        List<Link> linkList=linkService.getLinkList(null);
        model.addAttribute("linkList",linkList);
        return "Admin/Link/index";
    }
    @RequestMapping("/insert")
    public String insert(Model model){
        List<Link> linkList=linkService.getLinkList(null);
        model.addAttribute("linkList",linkList);
        return "Admin/Link/insert";
    }
    @RequestMapping("/insertSubmit")
    public String insertSubmit(Link link){
        linkService.insertLink(link);
        return "redirect:/admin/link";
    }
    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable("id")Integer id,Model model){
        Link link=linkService.getLinkById(id);
        model.addAttribute("linkCustom",link);
        List<Link> linkList=linkService.getLinkList(null);
        model.addAttribute("linkList",linkList);
        return "Admin/Link/edit";
    }
    @RequestMapping("/editSubmit")
    public String editSubmit(Link link){
        linkService.update(link);
        return "redirect:/admin/link";
    }
    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable("id")Integer id){
        linkService.delete(id);
        return "redirect:/admin/link";
    }
}
