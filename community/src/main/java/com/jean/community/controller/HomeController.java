package com.jean.community.controller;

import com.jean.community.entity.DiscussPost;
import com.jean.community.entity.User;
import com.jean.community.service.DiscusspostService;
import com.jean.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author The High Priestess
 * @date 2021/10/6 17:38
 */

@Controller
public class HomeController {

    @Autowired
    private DiscusspostService discusspostService;

    @Autowired
    private UserService userService;

    @RequestMapping(path="/index",method = RequestMethod.GET)
    public String getIndexPage(Model model) {
        List<DiscussPost> lists = discusspostService.findDiscussPosts(0, 0, 10);
        List<Map<String,Object>> discussPosts = new ArrayList<>();
        if (lists != null) {
            for(DiscussPost post:lists){
                Map<String, Object> map = new HashMap<>();
                map.put("post",post);
                User user = userService.findUserById(post.getId());
                map.put("user",user);
                discussPosts.add(map);
            }
        }
        model.addAttribute("discussPosts",discussPosts);
        return "/index";
    }
}
