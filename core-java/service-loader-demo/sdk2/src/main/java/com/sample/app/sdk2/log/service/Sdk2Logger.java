package com.sample.app.sdk2.log.service;

import com.sample.app.log.service.LogService;

public class Sdk2Logger implements LogService {

	@Override
	public void log(String msg) {
		System.out.println("From Sdk2Logger : " + msg);
	}

}
