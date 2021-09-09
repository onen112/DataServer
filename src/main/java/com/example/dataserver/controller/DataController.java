package com.example.dataserver.controller;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

public interface DataController {
    Object update(HttpServletRequest req, MultipartFile upData) throws IOException;
    Object delete(int id);
}
