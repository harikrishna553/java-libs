package com.sample.app.annotations;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import io.micronaut.context.annotation.Requires;
import jakarta.inject.Singleton;

@Requires(property = "datasource.url")
@Requires(property = "datasource.port")
@Requires(property = "datasource.username")
@Singleton
@Documented
@Retention(RUNTIME)
public @interface DataSource {

}
