package com.sample.app.transformation.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sample.app.constants.Constant;
import com.sample.app.exception.TransformationPayloadException;

/**
 * {"sourceField" : "sourceField", "newFieldName" : "newFieldName"}
 * 
 * @author h0g01ex
 *
 */
public class ReverseTransformationImpl extends TransformationImpl {
	private String sourceField;
	private String newFieldName;

	@Override
	public void validateRequestPayload() throws TransformationPayloadException {
		JsonElement sourceFieldElement = jsonObject.get(Constant.SOURCE_FIELD);

		if (sourceFieldElement == null) {
			throw new TransformationPayloadException(Constant.SOURCE_FIELD + " must present for reverse operation");
		}

		JsonElement newFieldElement = jsonObject.get(Constant.NEW_FIELD_NAME);

		if (newFieldElement == null) {
			throw new TransformationPayloadException(Constant.NEW_FIELD_NAME + " must present for reverse operation");
		}

		sourceField = sourceFieldElement.getAsString();
		newFieldName = newFieldElement.getAsString();

	}

	@Override
	public String getQueryString() throws TransformationPayloadException {
		super.getQueryString();

		StringBuilder stringBuilder = new StringBuilder();

		stringBuilder.append("REVERSE").append("(");
		stringBuilder.append(sourceField).append(")");
		stringBuilder.append(" ").append("AS").append(" ");
		stringBuilder.append(newFieldName);

		return stringBuilder.toString();
	}

}
