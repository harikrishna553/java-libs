package com.sample.app.types;

import org.apache.atlas.AtlasClientV2;
import org.apache.atlas.AtlasServiceException;

import com.fasterxml.jackson.core.JsonProcessingException;

public class TypeExistsCheckByName {

	public static void main(String[] args) throws AtlasServiceException, JsonProcessingException {

		AtlasClientV2 atlasClient = new AtlasClientV2(new String[] { "http://localhost:21000" },
				new String[] { "admin", "admin" });

		String typeName1 = "gcp_storage_bucket";
		String typeName2 = "testType";

		boolean typeWithName1Exists = atlasClient.typeWithNameExists(typeName1);
		boolean typeWithName2Exists = atlasClient.typeWithNameExists(typeName2);

		System.out.println("typeWithName1Exists : " + typeWithName1Exists);
		System.out.println("typeWithName2Exists : " + typeWithName2Exists);

	}
}