package com.sample.app.search;

import java.util.List;

import org.apache.atlas.AtlasClientV2;
import org.apache.atlas.AtlasServiceException;
import org.apache.atlas.model.discovery.AtlasSearchResult;
import org.apache.atlas.model.instance.AtlasEntityHeader;

public class BasicSearchHelloWorld {

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

		AtlasSearchResult atlasSearchResult = atlasClient.basicSearch("DataSet", null, null, false, 10, 0);
		printAtlasSearchResult(atlasSearchResult, "Get all the first 10 in a ‘DataSet’ type");

		atlasSearchResult = atlasClient.basicSearch(null, "sensitive_data", null, false, 10, 0);
		printAtlasSearchResult(atlasSearchResult, "Get all the entities attached with sensitive_data classification");

		atlasSearchResult = atlasClient.basicSearch(null, null, "sales", false, 10, 0);
		printAtlasSearchResult(atlasSearchResult, "Full text search for the string ‘sales’");

		atlasSearchResult = atlasClient.basicSearch(null, null, "sales", false, 10, 0);
		printAtlasSearchResult(atlasSearchResult, "Full text search for the string ‘sales’");

		atlasSearchResult = atlasClient.basicSearch("DataSet", null, "sales", false, Integer.MAX_VALUE, 0);
		printAtlasSearchResult(atlasSearchResult,
				"all the entities that are of type DataSet and contain the string sales’");

	}

}
