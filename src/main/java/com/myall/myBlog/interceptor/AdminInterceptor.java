package com.myall.myBlog.interceptor;

import com.myall.myBlog.enums.UserRole;
import com.myall.myBlog.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        User user=(User) httpServletRequest.getSession().getAttribute("user");
        if(user==null){
            httpServletResponse.sendRedirect("/login");
            return false;
        }
        if (!user.getUserRole().equals(UserRole.ADMIN.getValue())){
            httpServletResponse.sendRedirect("/403");
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
