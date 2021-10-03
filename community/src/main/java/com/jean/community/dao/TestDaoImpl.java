package com.jean.community.dao;
import com.jean.community.dao.TestDao;
import org.springframework.stereotype.Repository;

@Repository("testdao")
public class TestDaoImpl implements TestDao {


    @Override
    public String save() {
        return "JEAN TESTING";
    }
}
