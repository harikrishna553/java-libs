package com.sample.app.search;

import java.util.List;

import org.apache.atlas.AtlasClientV2;
import org.apache.atlas.AtlasServiceException;
import org.apache.atlas.model.discovery.AtlasSearchResult;
import org.apache.atlas.model.discovery.AtlasSearchResult.AttributeSearchResult;

import com.fasterxml.jackson.core.JsonProcessingException;

public class ApproximateCountOfEntitiesByAdvnacedSearch {

	public static void main(String[] args) throws AtlasServiceException, JsonProcessingException {
		AtlasClientV2 atlasClient = new AtlasClientV2(new String[] { "http://localhost:21000" },
				new String[] { "admin", "admin" });

		AtlasSearchResult atlasResult = atlasClient.dslSearch("DataSet select count()");

		AttributeSearchResult attributeSearchResult = atlasResult.getAttributes();

		List<List<Object>> values = attributeSearchResult.getValues();

		System.out.println("Approximate count : " + values.get(0).get(0));

	}

}