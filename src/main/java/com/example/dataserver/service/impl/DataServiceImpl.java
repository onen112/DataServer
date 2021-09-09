package com.example.dataserver.service.impl;

import com.example.dataserver.dao.DataMapper;
import com.example.dataserver.pojo.DataInfo;
import com.example.dataserver.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataServiceImpl implements DataService {
    @Autowired
    DataMapper dataMapper;
    @Override
    public int upload(DataInfo data) {

        return dataMapper.upload(data);
    }
}
