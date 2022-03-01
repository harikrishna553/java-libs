package com.sample.app.search;

import org.apache.atlas.AtlasClientV2;
import org.apache.atlas.AtlasServiceException;
import org.apache.atlas.model.discovery.AtlasSearchResult;
import org.apache.atlas.model.discovery.SearchParameters;

public class ApproximateCountOfDocs {
	
	public static void main(String[] args) throws AtlasServiceException {
		AtlasClientV2 atlasClient = new AtlasClientV2(new String[] { "http://localhost:21000" },
				new String[] { "admin", "admin" });
		
		SearchParameters searchParameters = new SearchParameters();
		searchParameters.setTypeName("DataSet");
		searchParameters.setOffset(0);
		searchParameters.setLimit(1);
		
		// Comment this if you want to consider sub types also
		searchParameters.setIncludeSubTypes(false);
		AtlasSearchResult atlasSearchResult = atlasClient.facetedSearch(searchParameters);
		
		System.out.println("Approximate count : " + atlasSearchResult.getApproximateCount());
		
	}

}
