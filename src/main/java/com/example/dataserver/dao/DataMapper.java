package com.example.dataserver.dao;

import com.example.dataserver.pojo.DataInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DataMapper {
    int upload(DataInfo dataInfo);
    int existsData(String md5,int uid);
    int delete(int id);

    List<DataInfo> getDatas(int id);

    List<DataInfo> getDatasByName(int id, String name);
}
