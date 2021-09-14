package com.example.dataserver.service;

import com.example.dataserver.pojo.DataInfo;

import java.util.List;


public interface DataService {
    int upload(DataInfo data);
    boolean existsData(String md5,int uid);
    boolean delete(int id,String md5);

    List<DataInfo> getDatas(int id);
    List<DataInfo> getDatas(int id,String name);
}
