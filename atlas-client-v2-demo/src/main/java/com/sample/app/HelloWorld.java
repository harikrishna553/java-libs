package com.sample.app;

import java.util.List;

import org.apache.atlas.AtlasClientV2;
import org.apache.atlas.AtlasServiceException;
import org.apache.atlas.model.SearchFilter;
import org.apache.atlas.model.typedef.AtlasEntityDef;
import org.apache.atlas.model.typedef.AtlasTypesDef;

public class HelloWorld {

	public static void main(String[] args) throws AtlasServiceException {
		AtlasClientV2 atlasClient = new AtlasClientV2(new String[] { "http://localhost:21000" },
				new String[] { "admin", "admin" });

		SearchFilter searchFilter = new SearchFilter();

		AtlasTypesDef atlasTypesDef = atlasClient.getAllTypeDefs(searchFilter);

		List<AtlasEntityDef> atlasEntityDefs = atlasTypesDef.getEntityDefs();

		for (AtlasEntityDef atlasEntityDef : atlasEntityDefs) {
			String typeName = atlasEntityDef.getName();
			System.out.println(typeName);

		}

	}
}
