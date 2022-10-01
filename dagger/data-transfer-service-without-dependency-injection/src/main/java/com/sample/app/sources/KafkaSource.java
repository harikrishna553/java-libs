package com.sample.app.sources;

public class KafkaSource {

	public String writeToKafka(String message) {
		System.out.println("Written data to Kafka");
		return "acknowledged";
	}

}
