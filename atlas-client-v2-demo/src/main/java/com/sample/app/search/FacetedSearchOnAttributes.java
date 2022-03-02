package com.sample.app.search;

import java.util.Arrays;
import java.util.List;

import org.apache.atlas.AtlasClientV2;
import org.apache.atlas.AtlasServiceException;
import org.apache.atlas.model.discovery.AtlasSearchResult;
import org.apache.atlas.model.discovery.SearchParameters;
import org.apache.atlas.model.instance.AtlasEntityHeader;

public class FacetedSearchOnAttributes {

	private static void printAtlasSearchResult(AtlasSearchResult atlasSearchResult, String message) {
		System.out.println("\n" + message);
		System.out.println("________________________________________");
		List<AtlasEntityHeader> entityHeaders = atlasSearchResult.getEntities();

		for (AtlasEntityHeader atlasEntityHeader : entityHeaders) {
			System.out.println("\t" + atlasEntityHeader.getAttribute("name"));
		}
	}

	public static void main(String[] args) throws AtlasServiceException {
		AtlasClientV2 atlasClient = new AtlasClientV2(new String[] { "http://localhost:21000" },
				new String[] { "admin", "admin" });

		SearchParameters searchParameters = new SearchParameters();
		searchParameters.setTypeName("DataSet");
		searchParameters.setOffset(0);
		searchParameters.setLimit(10);

		SearchParameters.FilterCriteria attributeFilter = new SearchParameters.FilterCriteria();
		attributeFilter.setAttributeName("name");
		attributeFilter.setAttributeValue("Attr");
		attributeFilter.setOperator(org.apache.atlas.model.discovery.SearchParameters.Operator.STARTS_WITH);

		searchParameters.setEntityFilters(attributeFilter);

		AtlasSearchResult atlasSearchResult = atlasClient.facetedSearch(searchParameters);

		printAtlasSearchResult(atlasSearchResult, "Get all the first 10 in a ‘DataSet’ type");
	}
}


