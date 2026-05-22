package com.sample.app;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KGroupedStream;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;

import java.util.Properties;

public class App {

    public static void main(String[] args) {

        Properties props = new Properties();

        props.put(
                StreamsConfig.APPLICATION_ID_CONFIG,
                "order-status-demo-app-1"
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

        /*
         * STEP 1
         * Read topic as KStream
         */
        KStream<String, String> ordersStream =
                builder.stream("orders-status-topic");

        /*
         * Print KStream events
         */
        ordersStream.foreach(
                (key, value) ->
                        System.out.println(
                                "[KSTREAM] " +
                                "ORDER_ID = " + key +
                                ", STATUS = " + value
                        )
        );

        /*
         * STEP 2
         * Convert KStream into KTable
         *
         * groupByKey() groups records by key
         * reduce() keeps latest value per key
         */
        KGroupedStream<String, String> groupedStream =
                ordersStream.groupByKey();

        KTable<String, String> ordersTable =
                groupedStream.reduce(
                        (oldValue, newValue) -> newValue
                );

        /*
         * Print KTable updates
         */
        ordersTable.toStream().foreach(
                (key, value) ->
                        System.out.println(
                                "[KTABLE] " +
                                "ORDER_ID = " + key +
                                ", CURRENT_STATUS = " + value
                        )
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