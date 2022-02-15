package com.sample.app.classifications;

import java.util.Arrays;

import org.apache.atlas.AtlasClientV2;
import org.apache.atlas.AtlasServiceException;
import org.apache.atlas.model.instance.AtlasClassification;

public class DeleteClassificationsOfEntityById {
	public static void main(String[] args) throws AtlasServiceException {
		AtlasClientV2 atlasClient = new AtlasClientV2(new String[] { "http://localhost:21000" },
				new String[] { "admin", "admin" });

		AtlasClassification sensitiveDataClassification = new AtlasClassification();
		sensitiveDataClassification.setTypeName("sensitive_data");

		AtlasClassification classifiedClassification = new AtlasClassification();
		classifiedClassification.setTypeName("classified");

		atlasClient.deleteClassifications("898c2aa1-d31a-4d0b-a1fd-7a76da6a4071",
				Arrays.asList(sensitiveDataClassification, classifiedClassification));

	}

}