package com.sample.app.types;

import org.apache.atlas.AtlasClientV2;
import org.apache.atlas.AtlasServiceException;

import com.fasterxml.jackson.core.JsonProcessingException;

public class TypeExistsCheckByGuid {

	public static void main(String[] args) throws AtlasServiceException, JsonProcessingException {

		AtlasClientV2 atlasClient = new AtlasClientV2(new String[] { "http://localhost:21000" },
				new String[] { "admin", "admin" });

		String guid1 = "bbadab1d-44c4-4b20-b385-b294f17e40af";
		String guid2 = "aaaaaaaa-44c4-4b20-b385-b294f17e40af";

		boolean typeWithGuid1Exists = atlasClient.typeWithGuidExists(guid1);
		boolean typeWithGuid2Exists = atlasClient.typeWithGuidExists(guid2);

		System.out.println("typeWithGuid1Exists : " + typeWithGuid1Exists);
		System.out.println("typeWithGuid2Exists : " + typeWithGuid2Exists);

	}
}