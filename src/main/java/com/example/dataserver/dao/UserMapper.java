package com.example.dataserver.dao;

import com.example.dataserver.pojo.UserInfo;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface UserMapper {
    UserInfo login(UserInfo user);
    int sigin(UserInfo user);
    int isSigin(String username);
}
