package com.example.dataserver.controller;

import javax.servlet.http.HttpServletRequest;

public interface DataController {
    Object update(HttpServletRequest req);
    Object delete(int id);
}
