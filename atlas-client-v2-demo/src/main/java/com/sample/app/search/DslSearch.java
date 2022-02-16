package com.sample.app.search;

import java.util.List;

import org.apache.atlas.AtlasClientV2;
import org.apache.atlas.AtlasServiceException;
import org.apache.atlas.model.discovery.AtlasSearchResult;
import org.apache.atlas.model.instance.AtlasEntityHeader;

public class DslSearch {

	public static void main(String[] args) throws AtlasServiceException {
		AtlasClientV2 atlasClient = new AtlasClientV2(new String[] { "http://localhost:21000" },
				new String[] { "admin", "admin" });

		AtlasSearchResult atlasSearchResult = atlasClient.dslSearch("DataSet where name like \"*Create*\"");

		System.out.println("\nMatched entity names");
		List<AtlasEntityHeader> entityHeaders = atlasSearchResult.getEntities();

		for (AtlasEntityHeader atlasEntityHeader : entityHeaders) {
			System.out.println("\t" + atlasEntityHeader.getAttribute("name"));
		}

	}

}
