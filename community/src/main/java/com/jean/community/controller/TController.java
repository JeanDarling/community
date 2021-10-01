package com.jean.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/test")
public class TController {
    @RequestMapping("/hello")
    @ResponseBody
    public String mycontrllo(){
        return "hello jean,this is your project";
    }
}
