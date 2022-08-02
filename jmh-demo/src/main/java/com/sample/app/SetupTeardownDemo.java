package com.sample.app;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class SetupTeardownDemo {
	
	@State(Scope.Benchmark)
    public static class MyState {

        @Setup(Level.Trial)
        public void setupTrailLevel() {
            System.out.println("\nsetupTrailLevel called\n");
        }
        
        @Setup(Level.Iteration)
        public void setupIterationLevel() {
            System.out.println("\n\tsetupIterationLevel called");
        }
        
        @Setup(Level.Invocation)
        public void setupInvocationLevel() {
            System.out.println("\n\t\tsetupInvocationLevel called");
        }

        @TearDown(Level.Trial)
        public void tearDownTrailLevel() {
            System.out.println("\ntearDownTrailLevel called\n");
        }
        
        @TearDown(Level.Iteration)
        public void tearDownIterationLevel() {
            System.out.println("\ttearDownIterationLevel called");
        }
        
        @TearDown(Level.Invocation)
        public void tearDownInvocationLevel() {
            System.out.println("\t\ttearDownInvocationLevel called");
        }
    }
	
	@Benchmark
	@BenchmarkMode(Mode.AverageTime)
    public void test(MyState myState) throws InterruptedException {
    	TimeUnit.SECONDS.sleep(2);
    	System.out.println("\t\ttest method executed");
    }
	
	
	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder()
				.include(SetupTeardownDemo.class.getSimpleName())
				.forks(2)
				.measurementIterations(1)
				.warmupIterations(1)
				.build();
		

		new Runner(opt).run();
	}

}


