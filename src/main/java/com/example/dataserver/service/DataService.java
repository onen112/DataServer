package com.example.dataserver.service;

import com.example.dataserver.pojo.DataInfo;

import javax.xml.crypto.Data;

public interface DataService {
    int upload(DataInfo data);

    boolean existsData(String md5);
}
