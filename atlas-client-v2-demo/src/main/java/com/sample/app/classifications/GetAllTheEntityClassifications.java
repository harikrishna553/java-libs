package com.sample.app.classifications;

import java.util.List;

import org.apache.atlas.AtlasClientV2;
import org.apache.atlas.AtlasServiceException;
import org.apache.atlas.model.instance.AtlasClassification;
import org.apache.atlas.model.instance.AtlasClassification.AtlasClassifications;

import com.fasterxml.jackson.core.JsonProcessingException;

public class GetAllTheEntityClassifications {

	public static void main(String[] args) throws AtlasServiceException, JsonProcessingException {
		AtlasClientV2 atlasClient = new AtlasClientV2(new String[] { "http://localhost:21000" },
				new String[] { "admin", "admin" });

		String entityGuid = "2b4cb4f9-b03e-4425-92de-8f1fc6c7e3aa";

		AtlasClassifications classifications = atlasClient.getClassifications(entityGuid);

		List<AtlasClassification> entityClassifications = classifications.getList();
		for (AtlasClassification atlasClassification : entityClassifications) {
			System.out.println(atlasClassification.getTypeName());
		}
	}

}