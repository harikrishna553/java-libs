package com.sample.app.converter;

import java.time.Instant;
import java.util.Date;

import org.dozer.CustomConverter;

import com.sample.app.model.AppLog;
import com.sample.app.model.TransactionLog;

public class AppLogToTransactionLogConverter implements CustomConverter {

	@Override
	public Object convert(Object destination, Object source, Class<?> destinationClass, Class<?> sourceClass) {

		if (source instanceof AppLog) {
			AppLog appLog = (AppLog) source;

			return new TransactionLog(appLog.getLogMessage(),
					Date.from(Instant.ofEpochMilli(appLog.getCreatedTimeInMillis())));

		}

		else if (source instanceof TransactionLog) {
			TransactionLog transactionLog = (TransactionLog) source;
			return new AppLog(transactionLog.getMessage(), transactionLog.getCreatedDate().getTime());
		}
		
		throw new IllegalArgumentException("Invalid types");
	}

}
