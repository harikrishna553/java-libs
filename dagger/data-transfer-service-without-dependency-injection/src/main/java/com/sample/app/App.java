package com.sample.app;

import com.sample.app.service.DataTransferService;
import com.sample.app.sources.KafkaSource;
import com.sample.app.sources.MySQLDataSource;

public class App {

	public static void main(String[] args) {
		MySQLDataSource mySQLDataSource = new MySQLDataSource();
		KafkaSource kafkaSource = new KafkaSource();

		DataTransferService DataTransferService = new DataTransferService(mySQLDataSource, kafkaSource);
		DataTransferService.transferData();

	}

}
