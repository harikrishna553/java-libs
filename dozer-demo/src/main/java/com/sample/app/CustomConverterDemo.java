package com.sample.app;

import java.util.Date;

import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.loader.api.FieldsMappingOptions;

import com.sample.app.converter.LogTimeConverter;
import com.sample.app.model.AppLog;
import com.sample.app.model.TransactionLog;

public class CustomConverterDemo {

	public static void main(String[] args) {
		DozerBeanMapper mapper = new DozerBeanMapper();

		BeanMappingBuilder foo = new BeanMappingBuilder() {

			@Override
			protected void configure() {
				mapping(TransactionLog.class, AppLog.class)
						.fields("createdDate", "createdTimeInMillis",
								FieldsMappingOptions.customConverter(LogTimeConverter.class))
						.fields("message", "logMessage");
			}
		};
		mapper.addMapping(foo);

		TransactionLog transactionLog1 = new TransactionLog("Unable to connect to database", new Date());
		AppLog appLog = mapper.map(transactionLog1, AppLog.class);
		TransactionLog transactionLog2 = mapper.map(appLog, TransactionLog.class);

		System.out.println(transactionLog1);
		System.out.println(appLog);
		System.out.println(transactionLog2);
	}

}