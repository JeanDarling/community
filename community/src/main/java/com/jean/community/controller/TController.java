package com.jean.community.controller;

import com.jean.community.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/test")
public class TController {

    @Autowired
    private TestService service;

    @RequestMapping("/hello")
    @ResponseBody
    public String mycontrllo(){
        return "hello jean,this is your project";
    }

    @RequestMapping("/getDate")
    @ResponseBody
    public String getDate(){
        return service.find();
    }
}
