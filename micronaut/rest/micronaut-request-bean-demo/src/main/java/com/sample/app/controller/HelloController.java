package com.sample.app.controller;

import java.util.*;

import javax.validation.Valid;

import com.sample.app.model.DemoBean;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.RequestBean;

@Controller("/hello")
public class HelloController {

	@Get(value = "/{entity-name}{?country,state}", produces = MediaType.APPLICATION_JSON)
	public Map<String, String> demo(@Valid @RequestBean DemoBean demoBean) {
		
		Map<String, String> map = new HashMap<>();
		map.put("entityName", demoBean.getEntityName());
		map.put("country", demoBean.getCountry());
		map.put("state", demoBean.getState());
		
		return map;
	}

}
