package com.aaa.lee.homework.mapper;

import com.aaa.lee.homework.model.User;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(User record);

    User selectByPrimaryKey(Long id);

    List<User> selectAll();

    int updateByPrimaryKey(User record);

    /**
     * @description 养成写注释的习惯
     *      通过账号和密码查询用户信息
     * @return
     */
    User selectUserByUsernamePassword(User user);
}