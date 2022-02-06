package com.sample.app.model;

public class Thread {
	private String name;
	private ThreadState threadState;

	public Thread() {
	}

	public Thread(String name, ThreadState threadState) {
		this.name = name;
		this.threadState = threadState;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ThreadState getThreadState() {
		return threadState;
	}

	public void setThreadState(ThreadState threadState) {
		this.threadState = threadState;
	}

	@Override
	public String toString() {
		return "Thread [name=" + name + ", threadState=" + threadState + "]";
	}

}
