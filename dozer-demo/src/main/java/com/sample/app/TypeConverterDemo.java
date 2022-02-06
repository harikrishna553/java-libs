package com.sample.app;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

import org.dozer.DozerBeanMapper;

import com.sample.app.model.AppLog;
import com.sample.app.model.TransactionLog;

public class TypeConverterDemo {

	public static void main(String[] args) throws IOException {
		DozerBeanMapper mapper = new DozerBeanMapper();
		mapper.setMappingFiles(Arrays.asList("appLog-to-transactionLog-mapping.xml"));
		
		TransactionLog transactionLog1 = new TransactionLog("Unable to connect to database", new Date());
		AppLog appLog = mapper.map(transactionLog1, AppLog.class);
		TransactionLog transactionLog2 = mapper.map(appLog, TransactionLog.class);

		System.out.println(transactionLog1);
		System.out.println(appLog);
		System.out.println(transactionLog2);
	}
}
