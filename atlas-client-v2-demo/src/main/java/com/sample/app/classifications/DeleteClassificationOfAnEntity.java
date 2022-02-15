package com.sample.app.classifications;

import org.apache.atlas.AtlasClientV2;
import org.apache.atlas.AtlasServiceException;

public class DeleteClassificationOfAnEntity {
	public static void main(String[] args) throws AtlasServiceException {
		AtlasClientV2 atlasClient = new AtlasClientV2(new String[] { "http://localhost:21000" },
				new String[] { "admin", "admin" });

		atlasClient.deleteClassification("898c2aa1-d31a-4d0b-a1fd-7a76da6a4071", "sensitive_data");

	}

}
