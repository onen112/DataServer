package com.example.dataserver.controller;

import com.example.dataserver.pojo.UserInfo;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public interface UserController {
    Object login(UserInfo user, HttpServletRequest request) throws UnsupportedEncodingException;
    Object sigin(UserInfo user) throws NoSuchAlgorithmException, UnsupportedEncodingException;
}
