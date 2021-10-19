package com.myall.myBlog.controller.admin;

import com.myall.myBlog.pojo.Options;
import com.myall.myBlog.service.OptionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/options")
public class BackOptionsController {
    @Autowired
    OptionsService optionsService;
    @RequestMapping("")
    public String index(Model model){
        Options options = optionsService.getOptions();
        model.addAttribute("option",options);
        return "Admin/Options/index";
    }
}
