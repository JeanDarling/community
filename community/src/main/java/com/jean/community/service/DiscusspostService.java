package com.jean.community.service;

import com.jean.community.dao.DiscussPostMapper;
import com.jean.community.entity.DiscussPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author The High Priestess
 * @date 2021/10/6 17:22
 */

@Service
public class DiscusspostService {

    @Autowired
    private DiscussPostMapper discussPostMapper;

    public List<DiscussPost> findDiscussPosts(int userId, int offset, int limit){
        return discussPostMapper.selectDiscussPost(userId,offset,limit);
    }

    public int findDiscussPosts(int userId){
        return discussPostMapper.selectDiscussPostRows(userId);
    }

}
