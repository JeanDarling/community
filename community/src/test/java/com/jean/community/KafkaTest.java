package com.jean.community;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by The High Priestess
 *
 * @description kafka测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class )
public class KafkaTest {

    @Autowired
    private KafkaProducer KafkaProducer;

    @Test
    public void kafkaTest(){
        KafkaProducer.sendMessage("test","hello");
        KafkaProducer.sendMessage("test","jean");

        try{
            Thread.sleep(1000*10);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

@Component
class KafkaProducer{

    @Autowired
    private KafkaTemplate kafkaTemplate;

    public void sendMessage(String topic, String content) {
        kafkaTemplate.send(topic,content);
    }
}

@Component
class KafkaConsumer {

    @KafkaListener(topics={"test"})
    public void handleMessage(ConsumerRecord record){
        System.out.println(record.value());
    }
}