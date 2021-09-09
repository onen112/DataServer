package com.example.dataserver.pojo;

import lombok.Data;

@Data
public class DataInfo {
    private int id;
    private int uid;
    private String name;
    private String md5;
    private String path;
    private long size;
    private String updatetime;
    private String contenttype;
    private String objectUrl;
}
