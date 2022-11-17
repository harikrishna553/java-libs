package com.sample.app.event.annotation;

import io.micronaut.aop.Adapter;
import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.core.annotation.Indexed;

import java.lang.annotation.*;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Retention(RUNTIME)
@Target({ ElementType.ANNOTATION_TYPE, ElementType.METHOD })
@Adapter(ApplicationEventListener.class)
@Indexed(ApplicationEventListener.class)
@Inherited
public @interface MyEventListener {
	
}