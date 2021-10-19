package com.myall.myBlog.service;

import com.myall.myBlog.pojo.User;

import java.util.List;

public interface UserService {
    User getUserById(Integer id);
    User getUserByName(String userName);
    User getUserByEmail(String userEmail);
    User getUserByNameOrEmail(String userNameOrEmail);
    User insertUser(User user);
    void updateUser(User user);
    void deleteUser(Integer id);
    List<User> getUserList();
}
