package com.sample.app;

import java.util.function.Function;

import io.github.resilience4j.retry.Retry;

public class DecorateAFuncion {

	public static void main(String[] args) {
		Retry retry = Retry.ofDefaults("someRemoteService");

		StringUpperService remoteService1 = new StringUpperService(3);

		Function<String, String> func = new Function<String, String>() {

			@Override
			public String apply(String t) {

				return remoteService1.toUpper(t);
			}

		};

		Function<String, String> decoratedFunction =  Retry.decorateFunction(retry, func);
		
		String result = decoratedFunction.apply("hello");
		
		System.out.println(result);

	}

}
