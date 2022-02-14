package com.sample.app.classifications;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.atlas.AtlasClientV2;
import org.apache.atlas.AtlasServiceException;
import org.apache.atlas.model.instance.AtlasClassification;

public class AddClassificationsToAnEntityByUniqueAttributes {

	public static void main(String[] args) throws AtlasServiceException {
		AtlasClientV2 atlasClient = new AtlasClientV2(new String[] { "http://localhost:21000" },
				new String[] { "admin", "admin" });

		Map<String, String> uniqAttributes = new HashMap<>();
		uniqAttributes.put("qualifiedName", "AttributeTypeMap_DEMO1");

		AtlasClassification sensitiveDataClassification = new AtlasClassification();
		sensitiveDataClassification.setTypeName("sensitive_data");
		sensitiveDataClassification.setPropagate(false);
		// This is a mandatory attribute for custom classification sensitive_data
		sensitiveDataClassification.setAttribute("durationInDays", 10);

		AtlasClassification classifiedClassification = new AtlasClassification();
		classifiedClassification.setTypeName("classified");
		classifiedClassification.setPropagate(false);

		atlasClient.addClassifications("DemoType4", uniqAttributes,
				Arrays.asList(sensitiveDataClassification, classifiedClassification));

	}

}