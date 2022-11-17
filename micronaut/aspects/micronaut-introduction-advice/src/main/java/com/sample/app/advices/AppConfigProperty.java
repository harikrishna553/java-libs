package com.sample.app.advices;

import io.micronaut.aop.Introduction;
import io.micronaut.context.annotation.Bean;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Introduction
@Bean
@Documented
@Retention(RUNTIME)
@Target({ TYPE, ANNOTATION_TYPE, METHOD })
public @interface AppConfigProperty {
	String value() default "";
}