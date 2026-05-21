package com.sample.app;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;

import java.util.Properties;

public class App {

    public static void main(String[] args) {

        Properties props = new Properties();

        props.put(
                StreamsConfig.APPLICATION_ID_CONFIG,
                "completed-orders-app"
        );

        props.put(
                StreamsConfig.BOOTSTRAP_SERVERS_CONFIG,
                "localhost:9092"
        );

        props.put(
                StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG,
                Serdes.String().getClass()
        );

        props.put(
                StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG,
                Serdes.String().getClass()
        );

        StreamsBuilder builder = new StreamsBuilder();

        KStream<String, String> ordersStream =
                builder.stream("orders-input-topic");

        KStream<String, String> completedOrdersStream =
                ordersStream.filter(
                        (key, value) ->
                                value.equals("COMPLETED")
                );

        completedOrdersStream.to(
                "completed-orders-topic"
        );

        KafkaStreams streams =
                new KafkaStreams(
                        builder.build(),
                        props
                );

        streams.start();

        Runtime.getRuntime().addShutdownHook(
                new Thread(streams::close)
        );
    }
}