package com.sample.app.transformation;

import com.sample.app.exception.TransformationPayloadException;

public interface Transformation {
	
	public void validateRequestPayload() throws TransformationPayloadException;

	public String getQueryString() throws TransformationPayloadException;
}
