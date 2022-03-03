package com.sample.app.glossary;

import org.apache.atlas.AtlasClientV2;
import org.apache.atlas.AtlasServiceException;

public class DeleteGlossaryCategoryById {

	public static void main(String[] args) throws AtlasServiceException {
		AtlasClientV2 atlasClient = new AtlasClientV2(new String[] { "http://localhost:21000" },
				new String[] { "admin", "admin" });

		String categoryId = "80e199fb-f924-4470-adc1-8227b5650f6c";

		atlasClient.deleteGlossaryCategoryByGuid(categoryId);
	}

}
