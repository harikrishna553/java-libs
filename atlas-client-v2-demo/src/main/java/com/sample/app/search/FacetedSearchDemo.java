package com.sample.app.search;

import java.util.List;

import org.apache.atlas.AtlasClientV2;
import org.apache.atlas.AtlasServiceException;
import org.apache.atlas.SortOrder;
import org.apache.atlas.model.discovery.AtlasSearchResult;
import org.apache.atlas.model.discovery.SearchParameters;
import org.apache.atlas.model.discovery.SearchParameters.FilterCriteria;
import org.apache.atlas.model.discovery.SearchParameters.Operator;
import org.apache.atlas.model.instance.AtlasEntityHeader;

public class FacetedSearchDemo {

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
		AtlasSearchResult atlasSearchResult = atlasClient.facetedSearch(searchParameters);
		printAtlasSearchResult(atlasSearchResult, "Get all the first 10 in a ‘DataSet’ type");

		searchParameters = new SearchParameters();
		searchParameters.setTypeName("DataSet");
		searchParameters.setOffset(0);
		searchParameters.setLimit(10);
		searchParameters.setSortBy("name");
		searchParameters.setSortOrder(SortOrder.ASCENDING);
		atlasSearchResult = atlasClient.facetedSearch(searchParameters);
		printAtlasSearchResult(atlasSearchResult, "Get all the first 10 in a ‘DataSet’ type and sort by name");

		searchParameters = new SearchParameters();
		searchParameters.setClassification("sensitive_data");
		searchParameters.setOffset(0);
		searchParameters.setLimit(10);
		atlasSearchResult = atlasClient.facetedSearch(searchParameters);
		printAtlasSearchResult(atlasSearchResult, "Get all the entities attached with sensitive_data classification");

		searchParameters = new SearchParameters();
		searchParameters.setTypeName("DataSet");
		searchParameters.setOffset(0);
		searchParameters.setLimit(10);
		searchParameters.setSortBy("name");
		searchParameters.setSortOrder(SortOrder.ASCENDING);

		FilterCriteria entityFilters = new FilterCriteria();
		entityFilters.setAttributeName("name");
		entityFilters.setOperator(Operator.CONTAINS);
		entityFilters.setAttributeValue("sale");
		searchParameters.setEntityFilters(entityFilters);
		atlasSearchResult = atlasClient.facetedSearch(searchParameters);
		printAtlasSearchResult(atlasSearchResult, "Get all the first 10 in a ‘DataSet’ type that contain name 'sale'");

		// Search by classification attribute
		searchParameters = new SearchParameters();
		searchParameters.setClassification("sensitive_data");
		searchParameters.setOffset(0);
		searchParameters.setLimit(10);
		searchParameters.setSortBy("name");
		searchParameters.setSortOrder(SortOrder.ASCENDING);

		FilterCriteria tagFilters = new FilterCriteria();
		tagFilters.setAttributeName("durationInDays");
		tagFilters.setOperator(Operator.EQ);
		tagFilters.setAttributeValue("10");
		searchParameters.setTagFilters(tagFilters);
		atlasSearchResult = atlasClient.facetedSearch(searchParameters);
		printAtlasSearchResult(atlasSearchResult,
				"Get all the first 10 in a ‘DataSet’ type that attached to 'sensitive_data' classification and the classificaiton attribute 'DurationInDays' is set to 10");
		
		// Free text search
		searchParameters = new SearchParameters();
		searchParameters.setQuery("sales");
		atlasSearchResult = atlasClient.facetedSearch(searchParameters);
		printAtlasSearchResult(atlasSearchResult,
				"get all the entities that contain string 'sales'");
		
		
	}

}

