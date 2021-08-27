package com.sample.app.service;

import com.sample.app.dto.SqlQueryRequestDto;
import com.sample.app.exception.TransformationPayloadException;

public interface QueryService {
	
	public String generateQuery(SqlQueryRequestDto sqlQueryRequestDto) throws TransformationPayloadException;

}
