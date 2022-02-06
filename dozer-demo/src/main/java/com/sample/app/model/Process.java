package com.sample.app.model;

public class Process {
	private String name;
	private ProcessState processState;

	public Process() {
	}

	public Process(String name, ProcessState processState) {
		this.name = name;
		this.processState = processState;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ProcessState getProcessState() {
		return processState;
	}

	public void setProcessState(ProcessState processState) {
		this.processState = processState;
	}

	@Override
	public String toString() {
		return "Process [name=" + name + ", processState=" + processState + "]";
	}

}
