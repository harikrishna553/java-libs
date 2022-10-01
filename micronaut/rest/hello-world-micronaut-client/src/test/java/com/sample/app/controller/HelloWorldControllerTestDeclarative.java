package com.sample.app.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.sample.app.client.interfaces.HelloWorldClient;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import reactor.core.publisher.Mono;

@MicronautTest 
public class HelloWorldControllerTestDeclarative {
	@Inject
    HelloWorldClient client; 

    @Test
    public void testHelloWorldResponse(){
        assertEquals("Hello World", Mono.from(client.hello()).block());
    }
}
