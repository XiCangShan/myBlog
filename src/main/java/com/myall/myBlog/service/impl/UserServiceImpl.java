package com.myall.myBlog.service.impl;

import com.myall.myBlog.enums.UserRole;
import com.myall.myBlog.mapper.*;
import com.myall.myBlog.pojo.Article;
import com.myall.myBlog.pojo.User;
import com.myall.myBlog.service.ArticleService;
import com.myall.myBlog.service.UserService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private CommentMapper commentMapper;
    public User getUserById(Integer id){
        return userMapper.getUserById(id);
    }
    public User getUserByName(String userName){
        return userMapper.getUserByName(userName);
    }
    public User getUserByEmail(String userEmail){
        return userMapper.getUserByEmail(userEmail);
    }
    public User getUserByNameOrEmail(String userNameOrEmail){
        return userMapper.getUserByNameOrEmail(userNameOrEmail);
    }

    @Override
    public void deleteUser(Integer id) {
        userMapper.deleteById(id);
        commentMapper.deleteByUserId(id);
        List<Integer> articleIds = articleMapper.getArticleIdsByUserId(id);
        if(articleIds!=null && articleIds.size() > 0){
            for (Integer articleId:articleIds) {
                articleService.delete(articleId);
            }
        }
    }

    @Override
    public List<User> getUserList() {
        List<User> userList = userMapper.getUserList();
        for (User user:userList) {
            user.setArticleCount(articleMapper.countArticleByUserId(user.getUserId()));
        }
        return userList;
    }

    public User insertUser(User user){
        user.setUserRegisterTime(new Date());
        user.setUserStatus(1);
        user.setUserRole(UserRole.USER.getValue());
        userMapper.insert(user);
        return user;
    }
    public void updateUser(User user){
        userMapper.updateUser(user);
    }
}
