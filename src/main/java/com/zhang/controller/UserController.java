package com.zhang.controller;

import com.zhang.domain.User;
import com.zhang.service.IUserService;
import com.zhang.utils.MessageDigestUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("user")
public class UserController {

    @Resource(name = "userService")
    private IUserService userService;

    // 登录功能
    @RequestMapping("login")
    public String login(HttpSession session, String account, String password) {
        if (account == null || password == null) {
            return "/login.jsp";
        }
        try {
            // 进行认证的代码（之前已解释过）
            Subject subject = SecurityUtils.getSubject(); // 当前对象的状态是"未认证"。
            AuthenticationToken token = new UsernamePasswordToken(account, MessageDigestUtils.encrypt("MD5", password.getBytes(), false));
            subject.login(token);
            // 认证成功后，则可以获取认证信息对象中存储的User对象。
            User user = (User) subject.getPrincipal(); // 通过subject的getPrincipal方法拿到对象，类型是Object，强转即可
            session.setAttribute("user", user);
            return "/funcpage.jsp";
        } catch (Exception e) {
            e.printStackTrace();
            // 认证不通过
            return "/login.jsp";
        }
    }
}
