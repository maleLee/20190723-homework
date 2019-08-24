package com.aaa.lee.homework.controller;

import com.aaa.lee.homework.model.User;
import com.aaa.lee.homework.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping("/turnLoginPage")
    public String turnLoginPage() {
        return "login";
    }

    /**
     * @description
     *      在判断期间，把判断的值写在需要判断的变量前可以防止空指针
     *      所有的controller中不允许出现任何和业务逻辑有关的判断！！！！！controller只做控制和跳转，并不参与业务逻辑
     * @param user
     * @return
     */
    @RequestMapping("/login")
    public String login(User user, ModelMap modelMap, HttpSession session) {
        Map<String, Object> resultMap = userService.login(user);
        String result = (String)resultMap.get("result");
        if("success".equals(result)) {
            // 从数据库中已经查询到了用户信息
            // 登录成功,存入session中
            User u = (User)resultMap.get("user");
            u.setPassword(null);
            session.setAttribute("user", u);
            // 需要从数据库中查询出当前登录用户的所有的权限信息
            return "redirect:permissions";
        } else {
            modelMap.addAttribute("msg", "暂无法找到用户，请重新登录");
            return "error";
        }
    }

    @RequestMapping("/permissions")
    public String permissions(ModelMap modelMap, HttpSession session) {
        // 1.从session获取用户信息
        User user = (User) session.getAttribute("user");
        String username = user.getUsername();
        Map<String, Object> resultMap = userService.permissions(username);
        String result = (String) resultMap.get("result");
        if("success".equals(result)) {
            // 说明该用户有权限
            modelMap.addAttribute("permissionList", resultMap.get("permissionList"));
            return "index";
        }
        return "error";
    }

}
