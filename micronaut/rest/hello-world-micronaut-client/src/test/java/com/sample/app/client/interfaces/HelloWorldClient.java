package com.sample.app.client.interfaces;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.annotation.Client;
import org.reactivestreams.Publisher;
import io.micronaut.core.async.annotation.SingleResult;

@Client("/hello")
public interface HelloWorldClient {

	@Get(consumes = MediaType.TEXT_PLAIN)
	@SingleResult
	Publisher<String> hello();
}