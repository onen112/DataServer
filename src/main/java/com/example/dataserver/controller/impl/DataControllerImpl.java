package com.example.dataserver.controller.impl;

import com.example.dataserver.controller.DataController;
import com.example.dataserver.pojo.DataInfo;
import com.example.dataserver.pojo.UserInfo;
import com.example.dataserver.service.impl.DataServiceImpl;
import com.example.dataserver.service.impl.UserServiceImpl;
import com.example.dataserver.util.MD5Util;
import com.obs.services.ObsClient;
import com.obs.services.model.PutObjectResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/data")
public class DataControllerImpl implements DataController {
    public static final String ak = "UYJHINAZVSTIJIBPEYPW";
    public static final String sk = "hCA7VJ3HlbAJkqspx8vHBX06xm7pvKgaiGXbU6Uj";
    public static final String endpoint = "obs.cn-east-2.myhuaweicloud.com";
    public static final String bname = "mydatatest";
    @Autowired
    MD5Util md5Util;
    @Autowired
    DataServiceImpl dataService;
    @Override
    @RequestMapping("/uploadData")
    public Object update(HttpServletRequest req, @RequestParam("upData") MultipartFile upData) throws IOException {
        HttpSession session = req.getSession();
        String msg = "";
        int state = -1;
        if(session == null){
            msg = "用户未登录";
        }else{
            UserInfo user =  (UserInfo) session.getAttribute("user");
            DataInfo data = new DataInfo();
            ObsClient obs = new ObsClient(ak,sk,endpoint);
            try {
                data.setName(upData.getOriginalFilename());
                data.setUid(user.getId());
                data.setSize(upData.getSize());
                data.setContenttype(req.getContentType());
                InputStream inputStream = upData.getInputStream();
                String md5 = md5Util.getMD5(inputStream) + data.getName();
                data.setMd5(md5);
                inputStream = upData.getInputStream();
                data.setContenttype(upData.getContentType());
                PutObjectResult putObjectResult = obs.putObject(bname, md5, inputStream);
                data.setObjectUrl(putObjectResult.getObjectUrl());
                dataService.upload(data);
                state = 0;
                msg = "文件上传成功";
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                obs.close();
            }
        }
        Map<String,Object> map = new HashMap<>();
        map.put("msg",msg);
        map.put("state",state);
        return map;
    }
    @Override
    public Object delete(int id) {
        return null;
    }
}
