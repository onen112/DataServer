package com.example.dataserver.controller.impl;

import com.example.dataserver.controller.UserController;
import com.example.dataserver.pojo.UserInfo;
import com.example.dataserver.service.impl.UserServiceImpl;
import com.example.dataserver.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserControllerImpl implements UserController {
    @Autowired
    MD5Util md5;
    @Autowired
    UserServiceImpl userService;

    @PostMapping("/login")
    public Object login(@RequestBody UserInfo user, HttpServletRequest request) throws UnsupportedEncodingException {
        int state;
        String msg;
        String username = user.getUsername();
        String password = user.getPassword();
        Map<String,Object> map = new HashMap<>();
        if(username == null || username.trim().equals("") || password == null || password.trim().equals("")){
            state = 0;
            msg = "登录失败用户名和密码不能为空";
        }else{
            user.setPassword(md5.getMD5(password));
            UserInfo ret = userService.login(user);
            if(ret != null && ret.getUsername().equals(user.getUsername())){
                state = 1;
                msg = "登录成功！";
                HttpSession session = request.getSession();
                session.setAttribute("user",ret);
            }else {
                state = 0;
                msg = "用户名或密码错误";
            }
        }
        map.put("state",state);
        map.put("msg",msg);
        return map;
    }


    @PostMapping("/sigin")
    public Object sigin(@RequestBody UserInfo user) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        int state = -1;
        String msg = "未知错误";
        String username = user.getUsername();
        String password = user.getPassword();
        if(username == null || username.trim().equals("") || password == null || password.trim().equals("")){
            state = 0;
            msg = "注册失败用户名和密码不能为空";
        }else if(userService.isSigin(username)) {
            state = -2;
            msg = "注册失败，当前用户名已经存在";
        }else{
            user.setPassword(md5.getMD5(password));
            user.setCreatetime(new Date());
            if(userService.sigin(user)){
                state = 1;
                msg = "注册成功";
            }
        }
        Map<String,Object> map = new HashMap<>();
        map.put("state",state);
        map.put("msg",msg);
        return map;

    }
}
