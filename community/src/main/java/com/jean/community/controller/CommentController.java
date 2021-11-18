package com.jean.community.controller;

import com.jean.community.entity.Comment;
import com.jean.community.entity.DiscussPost;
import com.jean.community.entity.Event;
import com.jean.community.event.EventProducer;
import com.jean.community.service.CommentService;
import com.jean.community.service.DiscusspostService;
import com.jean.community.util.CommunityConstant;
import com.jean.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

/**
 * @author The High Priestess
 * @date 2021/10/21 20:15
 */
@Controller
@RequestMapping("/comment")
public class CommentController implements CommunityConstant {

    @Autowired
    private EventProducer eventProducer;

    @Autowired
    private CommentService commentService;

    // 得到当前用户的ID
    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private DiscusspostService discusspostService;

    @RequestMapping(path = "/add/{discussPostId}", method = RequestMethod.POST)
    public String addComment(@PathVariable("discussPostId")int discussPostId, Comment comment){
        comment.setUserId(hostHolder.getUser().getId());
        comment.setStatus(0);
        comment.setCreateTime(new Date());
        commentService.addComment(comment);

        // 触发评论事件
        Event event = new Event()
                .setTopic(TOPIC_COMMENT)
                .setUserId(hostHolder.getUser().getId())
                .setEntityId(comment.getEntityId())
                .setEntityType(comment.getEntityType())
                .setData("postId", discussPostId);
        if(comment.getEntityType() == ENTITY_TYPE_POST) {
            DiscussPost target = discusspostService.findDiscussPostByID(comment.getEntityId());
            event.setEntityUserId(target.getUserId());
        }else if(comment.getEntityType() == ENTITY_TYPE_COMMENT) {
            Comment target = commentService.findCommentById(comment.getEntityId());
            event.setEntityUserId(target.getUserId());
        }

        eventProducer.fireEvent(event);

        return "redirect:/discuss/details/" +discussPostId;
    }

}
