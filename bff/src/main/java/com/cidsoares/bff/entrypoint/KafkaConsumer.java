package com.cidsoares.bff.entrypoint;

import com.cidsoares.bff.services.PeopleService;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class KafkaConsumer {

    private PeopleService peopleService;

    @KafkaListener(topics = "${kafka.response.topic}", groupId = "${kafka.group.id}")
    public void handle(String response,
                         @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key) {

        System.out.println("Gravando no Redis...");
        System.out.println("key:"+key);
        System.out.println("value:"+response);
        peopleService.saveResponse(key, response);
    }
}