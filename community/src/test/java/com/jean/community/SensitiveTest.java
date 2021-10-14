package com.jean.community;

import com.jean.community.util.SensitiveFilter;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author The High Priestess
 * @date 2021/10/14 18:23
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class )
public class SensitiveTest {
    @Autowired
    private SensitiveFilter sensitiveFilter;

    @Test
    public void testSeneitiveFilter() {
        String text = "这里可以赌博，可以吸毒，可以嫖娼，可以开票,哈哈哈！";
        text = sensitiveFilter.filter(text);
        System.out.println(text);

        String text1 = "这里可以赌博，可以☆吸☹毒☹，可以☺嫖☽娼，可以☼开♫票,哈哈哈！";
        text1 = sensitiveFilter.filter(text1);
        System.out.println(text1);
    }
}
