package com.jean.community.controller;

import com.jean.community.annotation.LoginRequired;
import com.jean.community.dao.UserMapper;
import com.jean.community.entity.User;
import com.jean.community.service.FollowService;
import com.jean.community.service.LikeService;
import com.jean.community.service.UserService;
import com.jean.community.util.CommunityConstant;
import com.jean.community.util.CommunityUtil;
import com.jean.community.util.HostHolder;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.weaver.ast.Var;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Map;

/**
 * @author The High Priestess
 * @date 2021/10/13 21:57
 */
@Controller
@RequestMapping("/user")
public class UserController implements CommunityConstant {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Value("${community.path.upload}")
    private String uploadPath;

    @Value("${community.path.domain}")
    private String domain;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Autowired
    private UserService userService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private LikeService likeService;

    @Autowired
    private FollowService followService;

    @LoginRequired
    @RequestMapping(path = "/setting", method = RequestMethod.GET)
    public String getSettingPage() {
        return "/site/setting";
    }

    @LoginRequired
    @RequestMapping(path = "/upload", method = RequestMethod.POST)
    public String uploadHeader(MultipartFile headerImage, Model model) {
        if (headerImage == null) {
            model.addAttribute("error", "????????????????????????");
            return "/site/setting";
        }
        //???????????????
        String originalFilename = headerImage.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));//???????????????????????????????????????????????????
        if (StringUtils.isBlank(suffix)) {
            model.addAttribute("error", "?????????????????????");
            return "/site/setting";
        }
        // ????????????????????????
        originalFilename = CommunityUtil.gennerateUUID() + suffix;
        // ???????????????????????????
        File dest = new File(uploadPath + "/" + originalFilename);
        try {
            //????????????
            headerImage.transferTo(dest); //???????????????????????????????????????
        } catch (IOException e) {
            log.error("??????????????????" + e.getMessage());
            throw new RuntimeException("??????????????????,?????????????????????" + e);
        }
        // ???????????????????????????????????????????????????web???????????????
        //http://localhost:8080/community/user/header/xxx.png
        //??????hostHoler?????????????????????
        User user = hostHolder.getUser();
        String headerUrl = domain + contextPath + "/user/header/" + originalFilename;
        userService.updateHeader(user.getId(), headerUrl);

        return "redirect:/index";
    }

    @RequestMapping(path = "/header/{fileName}", method = RequestMethod.GET)
    public void getHeader(@PathVariable("fileName") String fileName, HttpServletResponse response) {
        // ????????????????????????
        fileName = uploadPath + "/" + fileName;
        // ????????????
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        // ????????????
        response.setContentType("image" + suffix);
        try (FileInputStream fis = new FileInputStream(fileName);
             OutputStream os = response.getOutputStream();
        ) {
            byte[] buffer = new byte[1024];
            int b = 0;
            while ((b = fis.read(buffer)) != -1) {
                os.write(buffer, 0, b);
            }
        } catch (IOException e) {
            log.error("??????????????????" + e.getMessage());
        }
    }

    // ????????????
    @RequestMapping(path = "/updatePassword", method = RequestMethod.POST)
    public String updatePassword(String oldPassword, String newPassword, Model model) {
        User user = hostHolder.getUser();
        Map<String, Object> map = userService.updatePassword(user.getId(), oldPassword, newPassword);
        if (map == null || map.isEmpty()) {
            return "redirect:/logout";
        } else {
            model.addAttribute("oldPasswordMsg", map.get("oldPasswordMsg"));
            model.addAttribute("newPasswordMsg", map.get("newPasswordMsg"));
            return "/site/setting";
        }
    }

    // ????????????
    @RequestMapping(path = "/profile/{userId}", method = RequestMethod.GET)
    public String getProfilePage(@PathVariable("userId") int userId, Model model) {
        User user = userService.findUserById(userId);
        if (user == null) {
            throw new RuntimeException("?????????????????????");
        }
        // ??????
        model.addAttribute("user", user);
        // ????????????
        int count = likeService.findUserLikeCount(userId);
        model.addAttribute("userlike", count);

        // ????????????
        long followeeCount = followService.findFolloweeCount(userId, ENTITY_TYPE_USER);
        model.addAttribute("followeeCount", followeeCount);
        // ????????????
        long followerCount = followService.findFollowerCount(ENTITY_TYPE_USER,userId);
        model.addAttribute("followerCount", followerCount);
        // ??????????????????
        boolean hasFollowed = false;
        if (hostHolder.getUser() != null) {
            hasFollowed = followService.hasFollowed(hostHolder.getUser().getId(), ENTITY_TYPE_USER, userId);
        }
        model.addAttribute("hasFollowed", hasFollowed);

        return "/site/profile";
    }
}
