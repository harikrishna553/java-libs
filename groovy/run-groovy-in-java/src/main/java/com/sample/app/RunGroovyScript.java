package com.sample.app;

import groovy.lang.GroovyShell;

public class RunGroovyScript {
	public static void main(String[] args) {
		GroovyShell shell = new GroovyShell();
		String script = "println 'Hello, Groovy from Java!'";
		shell.evaluate(script);
	}
}
