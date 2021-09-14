package com.example.dataserver.service;

import com.example.dataserver.pojo.UserInfo;

public interface UserService {
    UserInfo login(UserInfo user);
    boolean sigin(UserInfo user);

    boolean isSigin(String username);

    boolean changeName(int id, String name);
    UserInfo getUserInfo(int id);
}
