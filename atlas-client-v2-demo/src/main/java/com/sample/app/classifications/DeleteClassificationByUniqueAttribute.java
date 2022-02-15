package com.sample.app.classifications;

import java.util.HashMap;
import java.util.Map;

import org.apache.atlas.AtlasClientV2;
import org.apache.atlas.AtlasServiceException;
import org.apache.atlas.model.instance.AtlasClassification;

public class DeleteClassificationByUniqueAttribute {
	public static void main(String[] args) throws AtlasServiceException {
		AtlasClientV2 atlasClient = new AtlasClientV2(new String[] { "http://localhost:21000" },
				new String[] { "admin", "admin" });

		AtlasClassification sensitiveDataClassification = new AtlasClassification();
		sensitiveDataClassification.setTypeName("sensitive_data");

		AtlasClassification classifiedClassification = new AtlasClassification();
		classifiedClassification.setTypeName("classified");

		String typeName = "DemoType1";
		Map<String, String> uniqueAttributes = new HashMap<>();
		uniqueAttributes.put("qualifiedName", "CreateEntity_DEMO1");

		atlasClient.removeClassification(typeName, uniqueAttributes, "classified");

	}

}