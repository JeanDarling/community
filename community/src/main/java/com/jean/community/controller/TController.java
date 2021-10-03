package com.jean.community.controller;

import com.jean.community.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

//    GET
//    /stu?currentPage=5&limit=20
    @RequestMapping(path="/stu",method = RequestMethod.GET)
    @ResponseBody
    public String getStu(
            @RequestParam(name="current" , required = false, defaultValue = "1")int current,
            @RequestParam(name="limit", required = false, defaultValue = "10")int limit
    ){
        System.out.println("current->"+current);
        System.out.println("limit->"+limit);
        return "ssstu..";
    }

//   /stu/123
    @RequestMapping(path="/stu/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String findOne(  @PathVariable("id") int id){
        System.out.println("id->"+id);
        return "id";
    }

//    POST提交请求
    @RequestMapping(path="/student" , method = RequestMethod.POST)
    @ResponseBody
    public String saveStu(String username, int age){
        System.out.println(username+"--"+age);
        return "post testing";
    }
    //响应HTML数据
    @RequestMapping(path="/teacher" , method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView saveTeacher(){
        ModelAndView mav = new ModelAndView();
        mav.addObject("name","张三");
        mav.addObject("id",66);
        mav.setViewName("/demo/view");
        return mav;
    }
//尽量
    @RequestMapping(path="/school",method = RequestMethod.GET)
    public String getSchool(Model model){
        model.addAttribute("name","清华");
        model.addAttribute("id",99);
        return "/demo/view";
    }

//    响应json对象（异步）
//    Java对象-》JSON字符串-》JS对象
    @RequestMapping(path="emp" , method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getEmp(){
        Map<String,Object> map=new HashMap<>();
        map.put("name","李四");
        map.put("age",13);
        map.put("password","1783");
        return map;
    }

    @RequestMapping(path="emps" , method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String,Object>> getEmps(){
        List<Map<String,Object>> list = new ArrayList<>();
        Map<String,Object> map=null;
        map=new HashMap<>();
        map.put("name","李四");
        map.put("age",13);
        map.put("password","1783");
        list.add(map);

         map=new HashMap<>();
        map.put("name","李wu");
        map.put("age",131);
        map.put("password","1asda");
        list.add(map);

        map=new HashMap<>();
        map.put("name","李asad");
        map.put("age",88);
        map.put("password","1sd3");
        list.add(map);

        return list;
    }
}
