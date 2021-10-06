package com.jean.community.service;

import com.jean.community.dao.UserMapper;
import com.jean.community.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author The High Priestess
 * @date 2021/10/6 17:28
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User findUserById(int id){
        return userMapper.selectById(id);
    }

}
