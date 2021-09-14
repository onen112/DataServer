package com.example.dataserver.util;

import com.obs.services.ObsClient;
import com.obs.services.model.PutObjectResult;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
@Component
public class ObsUtil {
    public static final String ak = "UYJHINAZVSTIJIBPEYPW";
    public static final String sk = "hCA7VJ3HlbAJkqspx8vHBX06xm7pvKgaiGXbU6Uj";
    public static final String endpoint = "obs.cn-east-2.myhuaweicloud.com";
    public static final String bname = "mydatatest";
    public PutObjectResult putObject(String name,InputStream inputStream){
        ObsClient obs = new ObsClient(ak,sk,endpoint);
        PutObjectResult putObjectResult = obs.putObject(bname, name, inputStream);
        try {
            obs.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return putObjectResult;
    }
}
