package com.jean.community;

import com.jean.community.dao.DiscussPostMapper;
import com.jean.community.dao.LoginTicketMapper;
import com.jean.community.dao.UserMapper;
import com.jean.community.entity.DiscussPost;
import com.jean.community.entity.LoginTicket;
import com.jean.community.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

/**
 * @author The High Priestess
 * @date 2021/10/6 16:00
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class )
public class MapperTests {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Autowired
    private LoginTicketMapper loginTicketMapper;

    @Test
    public void testSelectUser(){
        User user = userMapper.selectById(101);
        System.out.println(user);

        user=userMapper.selectByName("liubei");
        System.out.println(user);

        user = userMapper.selectByEmail("nowcoder25@sina.com");
        System.out.println(user);
    }


    @Test
    public void testDiscussPost(){
        List<DiscussPost> discussPosts = discussPostMapper.selectDiscussPost(0, 0, 10);
        discussPosts.forEach(el-> System.out.println(el));

        int i = discussPostMapper.selectDiscussPostRows(0);
        System.out.println(i);
    }

    @Test
    public void testInsertLoginticket(){
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setTicket("abcc");
        loginTicket.setUserId(101);
        loginTicket.setStatus(0);
        loginTicket.setExpired(new Date(System.currentTimeMillis()+1000 * 60 *10 ));
        loginTicketMapper.insertLoginTicket(loginTicket);

    }

    @Test
    public void testSelectloginTicket(){
        LoginTicket loginTicket = loginTicketMapper.selectLoginTicket("abcc");
        System.out.println(loginTicket);

        loginTicketMapper.updateStatus("abcc",1);
        loginTicket = loginTicketMapper.selectLoginTicket("abcc");
        System.out.println(loginTicket);

    }
}
