package com.sample.app.annotations;

import jakarta.inject.Qualifier;
import java.lang.annotation.Retention;

import io.micronaut.context.annotation.NonBinding;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Qualifier
@Retention(RUNTIME)
public @interface DogType {
	
	String type();
	
	@NonBinding
	String description() default "";

}
