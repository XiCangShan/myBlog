package com.myall.myBlog.mapper;

import com.myall.myBlog.pojo.User;

import java.util.List;

public interface UserMapper {
    int insert(User user);
    User getUserById(Integer id);
    User getUserByName(String userName);
    User getUserByEmail(String userEmail);
    User getUserByNameOrEmail(String userNameOrEmail);
    int deleteById(Integer id);
    int updateUser(User user);
    List<User> getUserList();
}
