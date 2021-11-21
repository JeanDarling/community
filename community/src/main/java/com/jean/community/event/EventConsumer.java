package com.jean.community.event;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jean.community.entity.DiscussPost;
import com.jean.community.entity.Event;
import com.jean.community.entity.Message;
import com.jean.community.service.DiscusspostService;
import com.jean.community.service.ElasticsearchService;
import com.jean.community.service.MessageService;
import com.jean.community.util.CommunityConstant;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by The High Priestess
 *
 * @description 事件消费者
 */
@Component
public class EventConsumer implements CommunityConstant {


    private static final Logger log = LoggerFactory.getLogger(EventConsumer.class);

    @Autowired
    private MessageService messageService;

    @Autowired
    private DiscusspostService discusspostService;

    @Autowired
    private ElasticsearchService elasticsearchService;

    @KafkaListener(topics = {TOPIC_COMMENT,TOPIC_LIKE,TOPIC_FOLLOW})
    public void handlerCommentMessage(ConsumerRecord record) {
        if(record == null || record.value() == null ) {
            log.error("消息的内容为空！");
            return;
        }

        Event event = JSONObject.parseObject(record.value().toString(), Event.class);
        if(event == null ) {
            log.error("消息格式错误！");
            return;
        }
        //发送站内通知
        Message message = new Message();
        message.setFromId(SYSTEM_USER_ID);
        message.setToId(event.getEntityUserId());
        message.setConversationId(event.getTopic());
        message.setCreateTime(new Date());

        Map<String, Object> content = new HashMap<>();
        content.put("userId", event.getUserId());
        content.put("entityType", event.getEntityType());
        content.put("entityId", event.getEntityId());

        if(!event.getData().isEmpty()) {
            for(Map.Entry<String, Object> entry: event.getData().entrySet()) {
                content.put(entry.getKey(), entry.getValue());
            }
        }

        message.setContent(JSONObject.toJSONString(content));
        messageService.addMessage(message);

    }

    // 消费发帖事件：
    @KafkaListener(topics = {TOPIC_PUBLISH})
    public void handlePublishMessage(ConsumerRecord record) {
        if(record == null || record.value() == null ) {
            log.error("消息的内容为空！");
            return;
        }

        Event event = JSONObject.parseObject(record.value().toString(), Event.class);
        if(event == null ) {
            log.error("消息格式错误！");
            return;
        }

        DiscussPost post = discusspostService.findDiscussPostByID(event.getEntityId());
        elasticsearchService.saveDiscussPost(post);
    }

}
