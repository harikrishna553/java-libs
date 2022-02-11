package com.sample.app.types;

import org.apache.atlas.AtlasClientV2;
import org.apache.atlas.AtlasServiceException;

import com.fasterxml.jackson.core.JsonProcessingException;

public class DeleteTypeByName {
	public static void main(String[] args) throws AtlasServiceException, JsonProcessingException {
		AtlasClientV2 atlasClient = new AtlasClientV2(new String[] { "http://localhost:21000" },
				new String[] { "admin", "admin" });

		String typeToDelete = "DemoType1";

		boolean isTypeExists = atlasClient.typeWithNameExists(typeToDelete);
		System.out.println("Is type with name '" + typeToDelete + "' exists : " + isTypeExists);

		System.out.println("\nTrying to delete the type : " + typeToDelete);
		atlasClient.deleteTypeByName(typeToDelete);

		isTypeExists = atlasClient.typeWithNameExists(typeToDelete);
		System.out.println("\nIs type with name '" + typeToDelete + "' exists : " + isTypeExists);

	}
}
