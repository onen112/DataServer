package com.example.dataserver.dao;

import com.example.dataserver.pojo.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


@Mapper
@Repository
public interface UserMapper {
    UserInfo login(UserInfo user);
    int sigin(UserInfo user);
    int isSigin(String username);
    int changeName(int id, String name);
    UserInfo getUserInfo(int id);
}
