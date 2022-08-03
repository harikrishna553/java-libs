package com.sample.app;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import com.sample.app.util.StringUtil;


public class JunitTestDemo {

	@Benchmark
	public void test() {
		String result = StringUtil.replaceMultipleSpaces("Hello there how are you....  ");
		assertEquals("Hello there how are you.... ", result);
	}

	@Test
	public void benchmark() throws Exception {
		Options opt = new OptionsBuilder()
				.include(this.getClass().getSimpleName())
				.forks(2)
				.measurementIterations(1)
				.warmupIterations(1)
				.build();
		

		new Runner(opt).run();
	}

}
