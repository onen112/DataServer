package com.example.dataserver.util;

import com.obs.services.ObsClient;
import com.obs.services.model.PutObjectResult;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
@Component
public class ObsUtil {
    private static final String ak = "UYJHINAZVSTIJIBPEYPW";
    private static final String sk = "hCA7VJ3HlbAJkqspx8vHBX06xm7pvKgaiGXbU6Uj";
    private static final String edndpoint = "s.cn-east-2.myhuaweicloud.com";
    private static ObsClient obsClient;
    static {
        obsClient = new ObsClient(ak,sk,edndpoint);
    }
    public PutObjectResult put(String name,InputStream inputStream){
        return obsClient.putObject("icloud-disk-1", name, inputStream);
    }
    public void close() throws IOException {
        obsClient.close();
    }
}
