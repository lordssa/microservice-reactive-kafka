package com.soares.app.entrypoint.messager;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {
 
    @KafkaListener(topics = "${kafka.request.topic}", groupId = "${kafka.group.id}")
    @SendTo
    public String handle(String idRequest,
                         @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key) {

        System.out.println("Dentro do gateway...");
        System.out.println("key:"+key);
        System.out.println("value:"+idRequest);

        return "Recebi"+idRequest;
    }
}