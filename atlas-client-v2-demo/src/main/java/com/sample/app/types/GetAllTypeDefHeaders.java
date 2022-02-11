package com.sample.app.types;

import java.util.List;

import org.apache.atlas.AtlasClientV2;
import org.apache.atlas.AtlasServiceException;
import org.apache.atlas.model.SearchFilter;
import org.apache.atlas.model.typedef.AtlasTypeDefHeader;

import com.fasterxml.jackson.core.JsonProcessingException;

public class GetAllTypeDefHeaders {

	public static void main(String[] args) throws AtlasServiceException, JsonProcessingException {

		AtlasClientV2 atlasClient = new AtlasClientV2(new String[] { "http://localhost:21000" },
				new String[] { "admin", "admin" });

		SearchFilter searchFilter = new SearchFilter();
		List<AtlasTypeDefHeader> atlasTypesDefHeaders = atlasClient.getAllTypeDefHeaders(searchFilter);

		System.out.println(atlasTypesDefHeaders);
	}
}