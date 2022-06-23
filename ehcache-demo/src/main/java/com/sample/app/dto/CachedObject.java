package com.sample.app.dto;

import java.time.Duration;

public class CachedObject {
	private Object data;
	private Duration expiryTime;

	public CachedObject(Object data, Duration expiryTime) {
		this.data = data;
		this.expiryTime = expiryTime;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Duration getExpiryTime() {
		return expiryTime;
	}

	public void setExpiryTime(Duration expiryTime) {
		this.expiryTime = expiryTime;
	}

}
