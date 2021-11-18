package com.jean.community.event;

import com.alibaba.fastjson.JSONObject;
import com.jean.community.entity.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by The High Priestess
 *
 * @description 事件生产者
 */
@Component
public class EventProducer {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    // 处理事件
    public void fireEvent(Event event){
        // 将指定事件发布到指定主题
        kafkaTemplate.send(event.getTopic(), JSONObject.toJSONBytes(event));

    }
}
