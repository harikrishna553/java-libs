package com.sample.app.virtual.threads.creation;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class VirtualThreadFactoryDemo2 {
	static class ModuleA {
		private final ThreadFactory factory;

		ModuleA(ThreadFactory factory) {
			this.factory = factory;
		}

		void process() {
			Thread t = factory.newThread(() -> {
				System.out.println("Module A running in " + Thread.currentThread());
			});
			t.start();
		}
	}

	static class ModuleB {
		private final ThreadFactory factory;

		ModuleB(ThreadFactory factory) {
			this.factory = factory;
		}

		void process() {
			Thread t = factory.newThread(() -> {
				System.out.println("Module B running in " + Thread.currentThread());
			});
			t.start();
		}
	}

	public static void main(String[] args) throws InterruptedException {
		// One shared factory
		ThreadFactory factory = Thread.ofVirtual().name("shared-thread-", 0).factory();

		ModuleA moduleA = new ModuleA(factory);
		ModuleB moduleB = new ModuleB(factory);

		moduleA.process();
		moduleB.process();
		
		// Some delay to let the virtual threads finish execution
		TimeUnit.SECONDS.sleep(1);
	}

}
