package com.jean.community.service;

import com.jean.community.dao.TestDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;



@Service
public class TestService {

    @Autowired
    private TestDao testDao;


    public String find(){
        return testDao.save();
    }

}
