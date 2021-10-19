package com.myall.myBlog.controller.admin;

import com.myall.myBlog.dto.JsonResult;
import com.myall.myBlog.enums.ArticleStatus;
import com.myall.myBlog.enums.UserRole;
import com.myall.myBlog.pojo.Article;
import com.myall.myBlog.pojo.Comment;
import com.myall.myBlog.pojo.User;
import com.myall.myBlog.service.ArticleService;
import com.myall.myBlog.service.CommentService;
import com.myall.myBlog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import static cn.hutool.extra.servlet.ServletUtil.getClientIP;

@Controller
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private CommentService commentService;

    @RequestMapping("/login")
    public String loginPage() {
        return "Admin/login";
    }

    @RequestMapping("/register")
    public String registerPage() {
        return "Admin/register";
    }

    //    注册
    @RequestMapping(value = "/registerSubmit", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult registerSubmit(User user) {
        User checkUserName = userService.getUserByName(user.getUserName());
        User checkUserEmail = userService.getUserByEmail(user.getUserEmail());
        if (checkUserName != null) {
            return new JsonResult().fail("用户名已存在");
        }
        if (checkUserEmail != null) {
            return new JsonResult().fail("邮箱已存在");
        }
        user.setUserRole(UserRole.USER.getValue());
        user.setUserStatus(1);
        user.setArticleCount(0);
        userService.insertUser(user);
        return new JsonResult().ok("注册成功");
    }

    //produces = {"text/plain;charset=UTF-8"}
//    指定请求头必须包含text/plain，设定返回编码charset=UTF-8
    @RequestMapping(value = "/loginVerify", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult loginVerify(String userNameOrEmail, String password, String rememberme, HttpServletRequest request, HttpServletResponse response) {
        User user = userService.getUserByNameOrEmail(userNameOrEmail);

        if (user == null) {
            return new JsonResult().fail("该用户不存在！");
        }
        if (password.equals(user.getUserPass())) {
            if (user.getUserStatus() == 0) {
                return new JsonResult().fail("该用户已被停用！");
            }
//          登录实现：  向共享域中添加当前已登录的用户 用于判断是否已登录
            request.getSession().setAttribute("user", user);
//            选择记住密码则创建3天的cookie
            if (rememberme != null) {
                Cookie nameCookie = new Cookie("username", userNameOrEmail);
                Cookie passwordCookie = new Cookie("password", password);
                nameCookie.setMaxAge(60 * 60 * 24 * 3);//有效期3天
                passwordCookie.setMaxAge(60 * 60 * 24 * 3);
                response.addCookie(nameCookie);
                response.addCookie(passwordCookie);
            }
//            更新最后登录时间和ip
            user.setUserLastLoginTime(new Date());
            user.setUserLastLoginIp(getClientIP(request));
            userService.updateUser(user);
            return new JsonResult().ok();
        } else {
            return new JsonResult().fail("密码错误！");
        }
    }

    @RequestMapping("/admin")
    public String admin(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        Integer userId = null;
//        是管理就将userId置空
        if (!user.getUserRole().equals(UserRole.ADMIN.getValue())) {
            userId = user.getUserId();
        }
        List<Article> articleList = articleService.getNewArticleListByUserId(user.getUserId(), 10);
        model.addAttribute("articleList", articleList);
        List<Comment> commentList = commentService.getNewCommentListByUserId(userId, 5);
        model.addAttribute("commentList",commentList);
        return "Admin/index";
    }
    @RequestMapping("/admin/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        session.invalidate();
        return "redirect:/login";
    }
}
