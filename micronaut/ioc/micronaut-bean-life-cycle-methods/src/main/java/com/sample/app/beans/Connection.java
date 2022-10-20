package com.sample.app.beans;

public class Connection {

	public Connection() {
		System.out.println("\nConnection constructor called\n");
	}

	public void stop() {
		System.out.println("Connection stopped");
	}

	public void destroy() {
		System.out.println("Connection destroyed");
	}
}
