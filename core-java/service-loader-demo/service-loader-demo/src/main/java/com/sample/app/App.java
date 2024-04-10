package com.sample.app;

import java.util.Iterator;
import java.util.ServiceLoader;

import com.sample.app.log.service.LogService;

public class App {
	public static void main(String[] args) {

		ServiceLoader<LogService> logServicesLoader = ServiceLoader.load(LogService.class);

		Iterator<LogService> logServiceIterator = logServicesLoader.iterator();

		while (logServiceIterator.hasNext()) {
			LogService logService = logServiceIterator.next();
			logService.log("Hello World");
		}
	}

}