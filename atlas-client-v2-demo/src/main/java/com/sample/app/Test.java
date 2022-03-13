package com.sample.app;

import java.util.HashSet;
import java.util.Set;

import org.apache.atlas.model.discovery.SearchParameters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sample.app.util.JsonUtil;

public class Test {
	
	public static void main(String[] args) throws JsonProcessingException {
		SearchParameters searchParameters = new SearchParameters();
		
		// Attributes to get in the response
		Set<String> attributes = new HashSet<> ();
		attributes.add("name");
		attributes.add("description");
		searchParameters.setAttributes(attributes);
		
		searchParameters.setClassification("sensitive_data");
		
		SearchParameters.FilterCriteria entityFilters = new SearchParameters.FilterCriteria();
		searchParameters.setEntityFilters(null);
		
		
		String json = JsonUtil.prettyPrintJson(searchParameters);
		
		System.out.println(json);
	}

}
