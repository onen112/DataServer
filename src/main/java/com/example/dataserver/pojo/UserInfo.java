package com.example.dataserver.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class UserInfo {
    private int id;
    private String username;
    private String name;
    private int state;
    private String password;
    private String createtime;
}
