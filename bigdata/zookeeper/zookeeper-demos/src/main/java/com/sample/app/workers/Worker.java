package com.sample.app.workers;

import com.sample.app.util.JsonUtil;

public class Worker {
	private String workerName;
	private String server;
	private String ipAddress;
	private String port;

	public Worker() {
	}

	public Worker(String workerName, String server, String ipAddress, String port) {
		this.workerName = workerName;
		this.server = server;
		this.ipAddress = ipAddress;
		this.port = port;
	}

	public String getWorkerName() {
		return workerName;
	}

	public void setWorkerName(String workerName) {
		this.workerName = workerName;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	@Override
	public String toString() {
		return JsonUtil.getJson(this);
	}

}
