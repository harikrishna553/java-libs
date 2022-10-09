package com.sample.app.annotations;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;

import jakarta.inject.Qualifier;

@Qualifier
@Retention(RUNTIME)
public @interface Poodle {

}
