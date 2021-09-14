package com.example.dataserver.controller;

import com.example.dataserver.pojo.UserInfo;
import com.example.dataserver.service.impl.UserServiceImpl;
import com.example.dataserver.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    MD5Util md5;
    @Autowired
    UserServiceImpl userService;

    @PostMapping("/login")
    public Object login(@RequestBody UserInfo user, HttpServletRequest request){
        int state;
        String msg;
        String username = user.getUsername();
        String password = user.getPassword();
        Map<String,Object> map = new HashMap<>();
        if(username == null || username.trim().equals("") || password == null || password.trim().equals("")){
            state = -1;
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
    public Object sigin(@RequestBody UserInfo user){
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
            user.setName(username);
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

    @RequestMapping("/getUserInfo")
    public Object getUserInfo(HttpServletRequest req){
        HttpSession session = req.getSession(false);
        String msg = "";
        UserInfo data = null;
        int state = -1;
        if(session == null || session.getAttribute("user") == null){
            state = 0;
            msg = "请先登录！";
        }else {
            state = 1;
            data = (UserInfo) session.getAttribute("user");
            msg = "查询成功！";
        }
        Map<String,Object> map = new HashMap<>();
        map.put("state",state);
        map.put("msg",msg);
        map.put("data",data);
        return map;
    }
    @PostMapping("/changeName")
    public Object changeName(@RequestBody Map<String,String> data, HttpServletRequest req){
        HttpSession session = req.getSession(false);
        String msg = "";
        int state = -1;
        String name = data.get("name");
        if(session == null || session.getAttribute("user") == null){
            msg = "请先登录";
            state = -1;
        }else{
            UserInfo user = (UserInfo) session.getAttribute("user");
            if( userService.changeName(user.getId(),name)){
                msg = "用户名修改成功！";
                state = 1;
                UserInfo user1 = userService.getUserInfo(((UserInfo) session.getAttribute("user")).getId());
                session.setAttribute("user",user1);
            }else{
                msg = "未知错误！";
                state = 0;
            }
        }
        Map<String,Object> map = new HashMap<>();
        map.put("state",state);
        map.put("msg",msg);
        return map;
    }
    @RequestMapping("/logOut")
    public Object logOut(HttpServletRequest req){
        HttpSession session = req.getSession(false);
        String msg = "";
        int state = -1;
        if(session == null || session.getAttribute("user") == null){
            msg = "用户未登录！";
        }else{
            msg = "退出成功！";
            state = 1;
        }
        Map<String,Object> map = new HashMap<>();
        map.put("msg",msg);
        map.put("state",state);
        return map;
    }
}
