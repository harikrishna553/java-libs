package com.sample.app.service;

import com.sample.app.sources.KafkaSource;
import com.sample.app.sources.MySQLDataSource;

public class DataTransferService {

	private MySQLDataSource mySQLDataSource;
	private KafkaSource kafkaSource;

	public DataTransferService(MySQLDataSource mySQLDataSource, KafkaSource kafkaSource) {
		this.mySQLDataSource = mySQLDataSource;
		this.kafkaSource = kafkaSource;
	}

	public void transferData() {
		String msg = mySQLDataSource.getData();
		kafkaSource.writeToKafka(msg);
	}
}
