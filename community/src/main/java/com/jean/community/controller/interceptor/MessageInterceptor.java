package com.jean.community.controller.interceptor;

import com.jean.community.entity.User;
import com.jean.community.service.MessageService;
import com.jean.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by The High Priestess
 *
 * @description 当前用户的消息数量
 */
@Component
public class MessageInterceptor implements HandlerInterceptor{

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private MessageService messageService;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        User user = hostHolder.getUser();
        if (user != null && modelAndView != null) {
            int letterUnreadCount = messageService.findUnReadLetters(user.getId(), null);
            int noticeUnreadCount = messageService.findUnreadCount(user.getId(), null);
            modelAndView.addObject("allUnreadCount", letterUnreadCount + noticeUnreadCount);

        }
    }

}
