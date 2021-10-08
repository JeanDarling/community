package com.jean.community;

import com.jean.community.util.MailClent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


/**
 * @author The High Priestess
 * @date 2021/10/7 22:47
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class MailTest {

    @Autowired
    private MailClent mailClent;

    @Autowired
    private TemplateEngine templateEngine;

    @Test
    public void testMail() {
        mailClent.sendMail("jeandarling@163.com","TEST", "welcome, this is your first email.");
    }

    @Test
    public void testHtmlMail(){
        Context context= new Context();
        context.setVariable("username", "sunday" );

        String content = templateEngine.process("/mail/demo",context);
        System.out.println(content);

        mailClent.sendMail("jeandarling@163.com","HTML", content);
    }
}
