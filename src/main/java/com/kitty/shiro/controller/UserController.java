package com.kitty.shiro.controller;

import com.kitty.shiro.service.UserService;
import org.springframework.stereotype.Controller;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String index(){
        return "index";
    }

    @RequestMapping("/see")
    public String see() {
        return "see";
    }

    @RequestMapping("/noAuth")
    public String noAuth() {
        return "noAuth";
    }
    @RequestMapping("/toLogin")
    public String toLogin() {
        return "login";
    }

    @RequestMapping("/accountError")
    public String accountError() {
        return "accountError";
    }


    @RequestMapping("/login")
    public String login(String name,String password,boolean rememberMe, Model model) {

        //1、获取subject
        Subject subject = SecurityUtils.getSubject();

        //2、封装用户数据
        UsernamePasswordToken token = new UsernamePasswordToken(name,password,rememberMe);

        //3、执行登录方法
        try {
            //交给Realm处理--->执行它的认证方法
            subject.login(token);
            //登录成功
            return "index";
        }catch (UnknownAccountException e){
            //登录失败:用户名不存在
            return "accountError";
        }catch (IncorrectCredentialsException e){
            //登录失败：密码错误
            return "accountError";
        }
    }
}
