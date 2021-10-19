package com.myall.myBlog.controller.admin;

import com.myall.myBlog.enums.NoticeStatus;
import com.myall.myBlog.pojo.Notice;
import com.myall.myBlog.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/notice")
public class BackNoticeController {
    @Autowired
   NoticeService noticeService;

    @RequestMapping("")
    public String index(Model model){
        List<Notice> noticeList=noticeService.getNoticeList(null);
        model.addAttribute("noticeList",noticeList);
        return "Admin/Notice/index";
    }
    @RequestMapping("/insert")
    public String insert(Model model){
        return "Admin/Notice/insert";
    }
    @RequestMapping("/insertSubmit")
    public String insertSubmit(Notice notice){
        noticeService.insert(notice);
        return "redirect:/admin/notice";
    }
    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable("id")Integer id,Model model){
        Notice notice=noticeService.getNoticeById(id);
        model.addAttribute("notice",notice);
        return "Admin/Notice/edit";
    }
    @RequestMapping("/editSubmit")
    public String editSubmit(Notice notice){
        noticeService.update(notice);
        return "redirect:/admin/notice";
    }
    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable("id")Integer id){
        noticeService.delete(id);
        return "redirect:/admin/notice";
    }
}
