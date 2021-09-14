package com.example.dataserver.service.impl;

import com.example.dataserver.dao.UserMapper;
import com.example.dataserver.pojo.UserInfo;
import com.example.dataserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    public UserInfo login(UserInfo user) {
        return userMapper.login(user);
    }
    public boolean sigin(UserInfo user) {
        return userMapper.sigin(user) > 0;
    }
    public boolean isSigin(String username) {
        return userMapper.isSigin(username) > 0;
    }
    @Override
    public boolean changeName(int id, String name) {
        return userMapper.changeName(id,name) > 0;
    }

    @Override
    public UserInfo getUserInfo(int id) {
       return userMapper.getUserInfo(id);
    }
}
