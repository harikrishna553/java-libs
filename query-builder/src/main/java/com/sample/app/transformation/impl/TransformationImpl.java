package com.sample.app.transformation.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sample.app.exception.TransformationPayloadException;
import com.sample.app.transformation.Transformation;

public abstract class TransformationImpl implements Transformation {
	JsonObject jsonObject;

	@Override
	public String getQueryString() throws TransformationPayloadException {
		this.validateRequestPayload();
		return null;
	}

	public JsonObject getJsonObject() {
		return jsonObject;
	}

	public void setJsonObject(JsonObject jsonObject) {
		this.jsonObject = jsonObject;
	}

}
