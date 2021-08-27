package com.sample.app.transformation.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sample.app.constants.Constant;
import com.sample.app.exception.TransformationPayloadException;

/**
 * { "sourceField": "city_st", "newFieldName": "sr_date" }
 * 
 * @author h0g01ex
 *
 */

public class LowerTransformationImpl extends TransformationImpl{
	private String sourceField;
	private String newFieldName;

	@Override
	public void validateRequestPayload() throws TransformationPayloadException {
		JsonElement sourceFieldElement = jsonObject.get(Constant.SOURCE_FIELD);

		if (sourceFieldElement == null) {
			throw new TransformationPayloadException(Constant.SOURCE_FIELD + " must present in lower transformation");
		}

		JsonElement newFieldNameElement = jsonObject.get(Constant.NEW_FIELD_NAME);
		if (newFieldNameElement == null) {
			throw new TransformationPayloadException(Constant.NEW_FIELD_NAME + " must present in lower transformation");
		}

		sourceField = sourceFieldElement.getAsString();
		newFieldName = newFieldNameElement.getAsString();

	}

	@Override
	public String getQueryString() throws TransformationPayloadException {
		super.getQueryString();
		StringBuilder stringBuilder = new StringBuilder();

		stringBuilder.append("LOWER").append("(").append(sourceField).append(")");
		stringBuilder.append(" ").append("AS").append(" ");
		stringBuilder.append(newFieldName);

		return stringBuilder.toString();
	}

}
