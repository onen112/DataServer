package com.example.dataserver.controller.impl;

import com.example.dataserver.controller.DataController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
@RestController
@RequestMapping("/data")
public class DataControllerImpl implements DataController {
    @Override
    public Object update(HttpServletRequest req) {
        return null;
    }

    @Override
    public Object delete(int id) {
        return null;
    }
}
