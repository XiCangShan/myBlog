package com.myall.myBlog.controller.admin;

import com.myall.myBlog.pojo.User;
import com.myall.myBlog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin/profile")
public class BackProfileController {
    @Autowired
    UserService userService;

    @RequestMapping("")
    public String index(HttpSession session, Model model){
        User sessionUser=(User) session.getAttribute("user");
        User user=userService.getUserById(sessionUser.getUserId());
        model.addAttribute("user",user);
        return "/Admin/User/profile";
    }
    @RequestMapping("/edit")
    public String editPage(HttpSession session,Model model){
        User sessionUser=(User) session.getAttribute("user");
        User user=userService.getUserById(sessionUser.getUserId());
        model.addAttribute("user",user);
        return "/Admin/User/editProfile";
    }
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public String save(User user,HttpSession session){
        User oldUser=(User)session.getAttribute("user");
        user.setUserId(oldUser.getUserId());
        userService.updateUser(user);
//        更新完应再获取完整的user对象注入session
        user=userService.getUserById(user.getUserId());
        session.setAttribute("user",user);
        return "redirect:/admin/profile";
    }
}
