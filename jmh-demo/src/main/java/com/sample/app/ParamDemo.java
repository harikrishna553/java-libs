package com.sample.app;

import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 3, time = 3, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 3, time = 3, timeUnit = TimeUnit.SECONDS)
@Fork(2)
@State(Scope.Benchmark)
public class ParamDemo {

	@Param({ "2978169", "3324329", "53", "71", "1191267381" })
	public long primes;

	@Param({ "2", "4", "8", "16" })
	public int certainty;
	
	@Benchmark
    public boolean bench() {
        return BigInteger.valueOf(primes).isProbablePrime(certainty);
    }

	public static void main(String[] args) throws IOException, RunnerException {
		Options opt = new OptionsBuilder()
				.include(ParamDemo.class.getSimpleName())
				.build();

		new Runner(opt).run();
	}
}
