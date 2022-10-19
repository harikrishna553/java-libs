package com.sample.app.annotations;

import io.micronaut.context.annotation.Requires;

@Requires(property = "datasource.url")
@Requires(property = "datasource.port")
@Requires(property = "datasource.username")
public @interface RequiresDataSource {

}
