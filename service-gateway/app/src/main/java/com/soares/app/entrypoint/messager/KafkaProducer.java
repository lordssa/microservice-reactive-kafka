package com.soares.app.entrypoint.messager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;
import reactor.kafka.sender.SenderRecord;

import java.util.HashMap;
import java.util.Map;

@Service
public class KafkaProducer {

    private KafkaSender kafkaProducer;

    private ObjectMapper objectMapper;

    private String bootstrapServer;

    @Value("${kafka.producer.people.topic}")
    private String peopleTopic;

    @Value("${kafka.producer.name}")
    private String gatewayName;

    public KafkaProducer(@Value("${kafka.broker}") String bootstrapServer) {
        final Map<String, Object> producerProps = new HashMap<>();
        producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);

        final SenderOptions<Integer, String> producerOptions = SenderOptions.create(producerProps);

        this.objectMapper = new ObjectMapper();
        this.kafkaProducer = KafkaSender.create(producerOptions);
    }

    public Mono<String> publish(final Object response, String key) {
        final var payload = toBinary(response);

        final SenderRecord<String, String, String> message =
                SenderRecord.create(peopleTopic,null,null, key, payload, key);

        return kafkaProducer.send(Mono.just(message))
                .then(Mono.just(key));
    }

    private String toBinary(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
