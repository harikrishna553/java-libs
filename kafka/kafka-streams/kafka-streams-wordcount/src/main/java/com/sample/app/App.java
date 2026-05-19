package com.sample.app;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;

import java.util.Arrays;
import java.util.Properties;

public class App {

    public static void main(String[] args) {

        System.out.println("==========================================");
        System.out.println("Starting Kafka Streams WordCount App");
        System.out.println("==========================================");

        /*
         * ==========================================
         * Kafka Streams Configuration
         * ==========================================
         */
        Properties props = new Properties();

        props.put(
                StreamsConfig.APPLICATION_ID_CONFIG,
                "word-count-application"
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

        /*
         * Optional but useful while learning
         */
        props.put(
                StreamsConfig.STATE_DIR_CONFIG,
                "/tmp/kafka-streams"
        );

        System.out.println("Kafka Streams Configuration:");
        System.out.println(props);

        /*
         * ==========================================
         * Create Stream Builder
         * ==========================================
         */
        StreamsBuilder builder = new StreamsBuilder();

        /*
         * ==========================================
         * Read from Input Topic
         * ==========================================
         */
        System.out.println(
                "Creating stream from topic: streams-plaintext-input"
        );

        KStream<String, String> inputStream =
                builder.stream("streams-plaintext-input");

        /*
         * Log incoming messages
         */
        inputStream.peek((key, value) -> {
            System.out.println(
                    "[INPUT] Received Message => key=" +
                    key +
                    ", value=" +
                    value
            );
        });

        /*
         * ==========================================
         * Convert to lowercase
         * ==========================================
         */
        KStream<String, String> lowerCaseStream =
                inputStream.mapValues(value -> {

                    String lowerCaseValue =
                            value.toLowerCase();

                    System.out.println(
                            "[LOWERCASE] " +
                            value +
                            " -> " +
                            lowerCaseValue
                    );

                    return lowerCaseValue;
                });

        /*
         * ==========================================
         * Split into words
         * ==========================================
         */
        KStream<String, String> wordsStream =
                lowerCaseStream.flatMapValues(value -> {

                    System.out.println(
                            "[SPLIT] Splitting sentence => " +
                            value
                    );

                    return Arrays.asList(
                            value.split("\\W+")
                    );
                });

        /*
         * Log generated words
         */
        wordsStream.peek((key, word) -> {

            System.out.println(
                    "[WORD] Generated Word => " +
                    word
            );
        });

        /*
         * ==========================================
         * Group and Count
         * ==========================================
         */
        System.out.println(
                "Grouping words and starting count aggregation..."
        );

        KTable<String, Long> wordCounts =
                wordsStream

                        .groupBy(
                                (key, word) -> word,
                                Grouped.with(
                                        Serdes.String(),
                                        Serdes.String()
                                )
                        )

                        .count();

        /*
         * ==========================================
         * Convert KTable to Stream
         * ==========================================
         */
        KStream<String, Long> wordCountStream =
                wordCounts.toStream();

        /*
         * Log word count updates
         */
        wordCountStream.peek((word, count) -> {

            System.out.println(
                    "[COUNT UPDATE] Word => " +
                    word +
                    ", Count => " +
                    count
            );
        });

        /*
         * ==========================================
         * Write to Output Topic
         * ==========================================
         */
        System.out.println(
                "Writing results to topic: streams-wordcount-output"
        );

        wordCountStream.to(
                "streams-wordcount-output"
        );

        /*
         * ==========================================
         * Build Topology
         * ==========================================
         */
        Topology topology = builder.build();

        System.out.println("==========================================");
        System.out.println("Kafka Streams Topology");
        System.out.println("==========================================");

        System.out.println(topology.describe());

        /*
         * ==========================================
         * Create Kafka Streams Application
         * ==========================================
         */
        KafkaStreams streams =
                new KafkaStreams(topology, props);

        /*
         * Listen for state changes
         */
        streams.setStateListener((newState, oldState) -> {

            System.out.println(
                    "[STATE CHANGE] " +
                    oldState +
                    " -> " +
                    newState
            );
        });

      
        /*
         * Graceful Shutdown
         */
        Runtime.getRuntime().addShutdownHook(

                new Thread(() -> {

                    System.out.println(
                            "Shutting down Kafka Streams Application..."
                    );

                    streams.close();

                    System.out.println(
                            "Kafka Streams Application Closed."
                    );
                })
        );

        /*
         * ==========================================
         * Start Application
         * ==========================================
         */
        System.out.println(
                "Starting Kafka Streams..."
        );

        streams.start();

        System.out.println(
                "Kafka Streams WordCount Application Started Successfully!"
        );
    }
}