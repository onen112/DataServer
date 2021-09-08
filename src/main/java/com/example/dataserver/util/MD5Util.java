package com.example.dataserver.util;

import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class MD5Util {
    private static MessageDigest md = null;
    static {
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }
    public String getMD5(String str) throws UnsupportedEncodingException {
        byte[] digest = md.digest(str.getBytes());
        return new String(digest,"utf-8");
    }
}