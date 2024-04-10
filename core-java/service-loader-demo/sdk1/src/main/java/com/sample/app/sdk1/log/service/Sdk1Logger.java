package com.sample.app.sdk1.log.service;

import java.util.Date;

import com.sample.app.log.service.LogService;

public class Sdk1Logger implements LogService {

	@Override
	public void log(String msg) {
		System.out.println("From Sdk1Logger " + new Date() + " : " + msg);
	}

}
