package com.jean.community;

import com.jean.community.dao.DiscussPostMapper;
import com.jean.community.dao.UserMapper;
import com.jean.community.entity.DiscussPost;
import com.jean.community.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

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
}
