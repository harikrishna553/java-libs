package com.sample.app.advices;

import io.micronaut.aop.Around;
import java.lang.annotation.*;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Retention(RUNTIME)
@Target({ TYPE, METHOD })
@Around
public @interface LogAdvice {

}
