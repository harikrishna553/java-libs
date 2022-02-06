package com.sample.app.model;

import java.util.Date;

public class TransactionLog {
	private String message;
	private Date createdDate;

	public TransactionLog() {
	}

	public TransactionLog(String message, Date createdDate) {
		this.message = message;
		this.createdDate = createdDate;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Override
	public String toString() {
		return "TransactionLog [message=" + message + ", createdDate=" + createdDate + "]";
	}

}
