package com.jean.community.controller;

import com.jean.community.entity.DiscussPost;
import com.jean.community.entity.User;
import com.jean.community.service.DiscusspostService;
import com.jean.community.service.UserService;
import com.jean.community.util.CommunityUtil;
import com.jean.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * @author The High Priestess
 * @date 2021/10/14 19:36
 */
@Controller
@RequestMapping("/discuss")
public class DiscussPostController {

    @Autowired
    private DiscusspostService discusspostService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private UserService userService;

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    @ResponseBody
    public String addDiscussPost(String title, String content) {
        User user = hostHolder.getUser();
        if (user == null) {
            return CommunityUtil.getJSONString(403, "你还没有权限");
        }

        DiscussPost post = new DiscussPost();
        post.setUserId(user.getId());
        post.setCreateTime(new Date());
        post.setTitle(title);
        post.setContent(content);
        discusspostService.addDiscussPost(post);
        // TODO 报错的情况，统一处理
        return CommunityUtil.getJSONString(0, "发布成功");
    }

    @RequestMapping(path = "/detail/{discussPostID}", method = RequestMethod.GET)
    public String getDiscussPost(@PathVariable("discussPostID") int discussPostID, Model model ){
        // 帖子
        DiscussPost discussPost = discusspostService.findDiscussPostByID(discussPostID);
        model.addAttribute("post",discussPost);
        // 作者
        User user = userService.findUserById(discussPost.getUserId());
        model.addAttribute("user",user);
        return "/site/discuss-detail";
    }
}
