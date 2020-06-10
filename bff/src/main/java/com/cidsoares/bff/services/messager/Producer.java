package com.cidsoares.bff.services.messager;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.concurrent.CompletableFuture;


@Component
public class Producer {

    @Value("${kafka.request.topic}")
    private String requestTopic;

    private static final Logger LOGGER = LoggerFactory.getLogger(Producer.class);

    private ReplyingKafkaTemplate<String, String, String> replyingKafkaTemplate;
 
    public Producer(final ReplyingKafkaTemplate<String, String, String> replyingKafkaTemplate) {
        this.replyingKafkaTemplate = replyingKafkaTemplate;
    }

    public CompletableFuture<String> send(final String idPeople, final String key){
        ProducerRecord<String, String> record = new ProducerRecord<>(requestTopic, null, key, idPeople);
        RequestReplyFuture<String, String, String> requestReplyFuture = replyingKafkaTemplate.sendAndReceive(record);

        CompletableFuture<String> completableResult = new CompletableFuture<String>() {
            @Override
            public boolean cancel(boolean mayInterruptIfRunning) {
                boolean result = requestReplyFuture.cancel(mayInterruptIfRunning);
                super.cancel(mayInterruptIfRunning);
                return result;
            }
        };
        // Add callback to the request sending result
        requestReplyFuture.getSendFuture().addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onSuccess(SendResult<String, String> sendResult) {
                LOGGER.info("Message recorded on partition {}", sendResult.getProducerRecord().partition());
            }
            @Override
            public void onFailure(Throwable t) {
                completableResult.completeExceptionally(t);
            }
        });

        // Add callback to the reply
        requestReplyFuture.addCallback(new ListenableFutureCallback<ConsumerRecord<String, String>>() {
            @Override
            public void onSuccess(ConsumerRecord<String, String> result) {
                completableResult.complete(result.value());
            }

            @Override
            public void onFailure(Throwable t) {
                completableResult.completeExceptionally(t);
            }
        });
        return completableResult;
    }
}