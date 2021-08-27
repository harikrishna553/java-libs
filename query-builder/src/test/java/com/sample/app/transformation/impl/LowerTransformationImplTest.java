package com.sample.app.transformation.impl;

import org.junit.Test;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sample.app.exception.TransformationPayloadException;

public class LowerTransformationImplTest {
	@Test
	public void test1() throws TransformationPayloadException {
		String json = "{ \"sourceField\": \"DOMAIN_NM\", \"newFieldName\": \"NAME_IN_LOWER\" }";
		
		LowerTransformationImpl lowerTransformationImpl = new LowerTransformationImpl();
		
		JsonElement jsonElement = JsonParser.parseString(json);
		lowerTransformationImpl.setJsonObject((JsonObject) jsonElement);
		
		lowerTransformationImpl.getQueryString();
		
		String query = lowerTransformationImpl.getQueryString();
		
		System.out.println(query);
	}
}
