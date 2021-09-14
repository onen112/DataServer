package com.example.dataserver.controller;

import com.example.dataserver.pojo.DataInfo;
import com.example.dataserver.pojo.UserInfo;
import com.example.dataserver.service.impl.DataServiceImpl;
import com.example.dataserver.service.impl.UserServiceImpl;
import com.example.dataserver.util.MD5Util;
import com.example.dataserver.util.ObsUtil;
import com.obs.services.ObsClient;
import com.obs.services.model.PutObjectResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/data")
public class DataController {
    public static final String ak = "UYJHINAZVSTIJIBPEYPW";
    public static final String sk = "hCA7VJ3HlbAJkqspx8vHBX06xm7pvKgaiGXbU6Uj";
    public static final String endpoint = "obs.cn-east-2.myhuaweicloud.com";
    public static final String bname = "mydatatest";
    @Autowired
    MD5Util md5Util;
    @Autowired
    DataServiceImpl dataService;
    @Autowired
    ObsUtil obsUtil;
    @ResponseBody
    @PostMapping("/uploadData")
    public Object update(HttpServletRequest req, @RequestParam("upData") MultipartFile upData) throws IOException {
        HttpSession session = req.getSession(false);
        String msg = "";
        int state = -1;
        if(session == null){
            msg = "用户未登录";
        }else{
            UserInfo user =  (UserInfo) session.getAttribute("user");
            if(upData.getSize() == 0){
                state = 0;
                msg = "请先选择文件！";
            }else{
                DataInfo data = new DataInfo();
                InputStream inputStream = upData.getInputStream();
                String md5 = md5Util.getMD5(inputStream) + upData.getOriginalFilename();
                if(dataService.existsData(md5,user.getId())){
                    msg = "该文件已经存在，请勿重复上传";
                    state = -1;
                }else {
                    try {
                        data.setName(upData.getOriginalFilename());
                        data.setUid(user.getId());
                        data.setSize(upData.getSize());
                        data.setContenttype(req.getContentType());
                        data.setMd5(md5);
                        inputStream = upData.getInputStream();
                        data.setContenttype(upData.getContentType());
                        ObsClient obs = new ObsClient(ak, sk, endpoint);
                        PutObjectResult putObjectResult = obs.putObject(bname, md5, inputStream);
                        data.setObjectUrl(putObjectResult.getObjectUrl());
                        obs.close();
                        dataService.upload(data);
                        state = 1;
                        msg = "文件上传成功";
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        Map<String,Object> map = new HashMap<>();
        map.put("msg",msg);
        map.put("state",state);
        return map;
    }
    @ResponseBody
    @PostMapping("/deleteData")
    public Object delete(@RequestBody DataInfo dataInfo, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        String msg = "";
        int state = -1;
        if(session == null || session.getAttribute("user") == null){
            msg = "用户未登录";
            state = -1;
        }else{
            UserInfo user = (UserInfo) session.getAttribute("user");
            ObsClient obsClient = new ObsClient(ak,sk,endpoint);
            String md5 = dataInfo.getMd5();
            if(dataService.existsData(md5,user.getId())){
                int id = dataInfo.getId();
                obsClient.deleteObject(bname, md5);
                try {
                    obsClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(dataService.delete(id,md5)){
                    msg = "文件删除成功！";
                    state = 1;
                }else{
                    state = 0;
                    msg = "出现未知错误，删除失败，请稍后重试";
                }
            }else{
                msg = "删除失败，未找到对应文件";
                state = 0;
            }
        }
        Map<String,Object> map = new HashMap<>();
        map.put("msg",msg);
        map.put("state",state);
        return map;
    }
    @ResponseBody
    @RequestMapping("/getDataList")
    public Object getDatas(HttpServletRequest req,String docName) {
        HttpSession session = req.getSession(false);
        int state = -1;
        String msg = "未知错误！";
        List<DataInfo>  dataList = null;
        Map<String,Object> data = null;
        if(session == null || session.getAttribute("user") == null){
            msg = "用户未登录";
            state = 0;
        }else{
                data = new HashMap<>();
                UserInfo user = (UserInfo) session.getAttribute("user");
                data.put("userInfo",user);
            if("".equals(docName)){
                dataList = dataService.getDatas(user.getId());
            }else{
                dataList = dataService.getDatas(user.getId(),docName);
            }
            data.put("dataList",dataList);
            state = 1;
            msg = "查找成功";
        }
        Map<String,Object> map = new HashMap<>();
        map.put("state",state);
        map.put("msg",msg);
        map.put("data",data);
        return map;
    }
}
