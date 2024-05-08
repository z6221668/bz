package com.browse.controller;

import com.browse.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebController {

    @Autowired
    private AppService appService;

    @RequestMapping(value = "/")
    public String login() {
        return "login.html";
    }

    @RequestMapping(value = "/register")
    public String register() {
        return "register.html";
    }

    @RequestMapping(value = "/web")
    public String web() {
        return "web.html";
    }

    @RequestMapping(value = "/predict")
    public String indexs() {
        return "predict.html";
    }

    @RequestMapping(value = "/result")
    public String resultInfo() {
        return "result.html";
    }
}
