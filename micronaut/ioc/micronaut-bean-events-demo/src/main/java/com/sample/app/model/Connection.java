package com.sample.app.model;

import io.micronaut.context.annotation.Prototype;
import jakarta.annotation.PostConstruct;

@Prototype
public class Connection {

	private Integer port;

	public Connection() {
		System.out.println("Connection constructor called");
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	@Override
	public String toString() {
		return "Connection [port=" + port + "]";
	}
	
	@PostConstruct
	public void initialize() {
		System.out.println("inside PostConstruct, Port has value " + this.port);
	}

}
