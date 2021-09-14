package com.example.dataserver.service.impl;

import com.example.dataserver.dao.DataMapper;
import com.example.dataserver.pojo.DataInfo;
import com.example.dataserver.service.DataService;
import com.obs.services.ObsClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

@Service
public class DataServiceImpl implements DataService {
    public static final String ak = "UYJHINAZVSTIJIBPEYPW";
    public static final String sk = "hCA7VJ3HlbAJkqspx8vHBX06xm7pvKgaiGXbU6Uj";
    public static final String endpoint = "obs.cn-east-2.myhuaweicloud.com";
    public static final String bname = "mydatatest";
    @Autowired
    DataMapper dataMapper;
    @Override
    public int upload(DataInfo data) {
        return dataMapper.upload(data);
    }

    @Override
    public boolean existsData(String md5,int uid) {
        return dataMapper.existsData(md5,uid) > 0;
    }

    @Override
    @Transactional
    public boolean delete(int id,String md5) {
        dataMapper.delete(id);
        ObsClient obs = new ObsClient(ak,sk,endpoint);
        if(obs.doesObjectExist(bname,md5)){
            //obs中文件未被删除，进行回滚事物
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
        return true;
    }

    @Override
    public List<DataInfo> getDatas(int id) {

        return dataMapper.getDatas(id);
    }

    @Override
    public List<DataInfo> getDatas(int id, String name) {
        return dataMapper.getDatasByName(id,name);
    }
}
