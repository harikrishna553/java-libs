package com.sample.app.transformation.impl;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sample.app.constants.Constant;
import com.sample.app.exception.TransformationPayloadException;

public class MergeTransformationImpl extends TransformationImpl {
	private String delimeter;
	private String newFieldName;
	private JsonArray sourceFieldsJsonArray;


	/**
	 * { "sourceFields": [ "week_start_date", "review_date" ], "delimter": ",",
	 * "newFieldName": "sr_date" }
	 * 
	 * @throws TransformationPayloadException
	 */
	public String getQueryString() throws TransformationPayloadException {
		super.getQueryString();
		StringBuilder builder = new StringBuilder();
		builder.append("CONCAT").append("(");

		int arraySize = sourceFieldsJsonArray.size();

		for (int i = 0; i < arraySize; i++) {
			JsonElement jsonElement1 = sourceFieldsJsonArray.get(i);

			builder.append(jsonElement1.getAsString());

			if (i != arraySize - 1) {
				builder.append(',').append("'").append(delimeter).append("'").append(',');
			}
		}
		builder.append(')').append(" ");

		builder.append(Constant.AS);
		builder.append(" ").append(newFieldName);
		return builder.toString();

	}

	public void validateRequestPayload() throws TransformationPayloadException {

		JsonElement deliemterJsonElement = jsonObject.get(Constant.DELIMETER);
		if (deliemterJsonElement == null) {
			throw new TransformationPayloadException(Constant.DELIMETER + " should set for merge transformation");
		}

		JsonElement newFiledNameJsonElement = jsonObject.get(Constant.NEW_FIELD_NAME);
		if (newFiledNameJsonElement == null) {
			throw new TransformationPayloadException(Constant.NEW_FIELD_NAME + " should set for merge transformation");
		}

		sourceFieldsJsonArray = jsonObject.getAsJsonArray(Constant.SOURCE_FIELDS);
		if (sourceFieldsJsonArray == null || sourceFieldsJsonArray.size() < 1) {
			throw new TransformationPayloadException(Constant.SOURCE_FIELDS + " should set for merge transformation");
		}
		delimeter = deliemterJsonElement.getAsString();
		newFieldName = newFiledNameJsonElement.getAsString();
	}

}
