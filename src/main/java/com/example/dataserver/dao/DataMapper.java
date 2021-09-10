package com.example.dataserver.dao;

import com.example.dataserver.pojo.DataInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DataMapper {
    int upload(DataInfo dataInfo);

    int existsData(String md5);
}
