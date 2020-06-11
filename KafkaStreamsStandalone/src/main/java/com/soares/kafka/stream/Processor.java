package com.soares.kafka.stream;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soares.kafka.stream.resource.MetaResource;
import com.soares.kafka.stream.resource.PeopleResource;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KStreamBuilder;
import java.io.IOException;
import java.util.Properties;

public class Processor {

    public static void main(String[] args) {
        Properties config = new Properties();
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "list-application-clustering");
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        var builder = new KStreamBuilder();
        // 1 - stream from Kafka

        KStream<String, String> gatewayPeople = builder.stream("gateway-people-out");

        KStream<String, String> response = gatewayPeople
                .peek((key, value) ->  System.out.println(value))
                .mapValues(value -> fromBinary(value, PeopleResource.class))
                .mapValues(peopleResource -> compute(peopleResource));

        response.to("gateway-reply");

        var streams = new KafkaStreams(builder, config);
        streams.cleanUp(); // only do this in dev - not in prod
        streams.start();

        // shutdown hook to correctly close the streams application
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));

    }

    private static String compute(PeopleResource peopleResource) {

        final var countContacts = peopleResource.getContacts().stream().count();

        final var metaResource = MetaResource
                .builder()
                .qtdContacts(countContacts)
                .build();

        return toBinary(peopleResource
                .toBuilder()
                .metaResource(metaResource)
                .build());

    }

    private static <T> T fromBinary(String object, Class<T> resultType) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(object, resultType);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private static String toBinary(Object object) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
