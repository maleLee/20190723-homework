package com.aaa.lee.homework.service;

import com.aaa.lee.homework.mapper.PermissionMapper;
import com.aaa.lee.homework.mapper.RoleMapper;
import com.aaa.lee.homework.mapper.UserMapper;
import com.aaa.lee.homework.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    /**
     * @description
     *      登录操作
     * @param user
     * @return
     */
    public Map<String, Object> login(User user) {
        User u = userMapper.selectUserByUsernamePassword(user);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if(null == u || 0L >= u.getId()) {
            // 没有从数据库中查询到用户信息
            resultMap.put("result", "error");
        } else {
            resultMap.put("result", "success");
            resultMap.put("user", u);
        }
        return resultMap;
    }

    /**
     * @description
     *      通过用户名查询出所有的权限信息
     * @param username
     * @return
     */
    public Map<String, Object> permissions(String username) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        // 2.通过用户名查询出所有的权限信息
        List<String> permissionString = permissionMapper.selectPermissionByUsername(username);
        // 3.判断(查询出的权限信息是否为空，如果为空则跳转错误页面，表示该登录用户没有任何权限)
        if("".equals(permissionString) || 0 >= permissionString.size()) {
            // 表示权限没有查询到，也就是说该用户并没有任何权限
            resultMap.put("result", "no_permission");
        } else {
            // 4.如果不为空，输入页面
            resultMap.put("result", "success");
            resultMap.put("permissionList", permissionString);
        }
        return resultMap;
    }

}
