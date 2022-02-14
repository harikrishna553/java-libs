package com.sample.app.classifications;

import java.util.Arrays;

import org.apache.atlas.AtlasClientV2;
import org.apache.atlas.AtlasServiceException;
import org.apache.atlas.model.instance.AtlasClassification;

public class AddClassificationsToAnEntityByGuid {

	public static void main(String[] args) throws AtlasServiceException {
		AtlasClientV2 atlasClient = new AtlasClientV2(new String[] { "http://localhost:21000" },
				new String[] { "admin", "admin" });

		String entityGuid = "66875fe8-c5f8-40c2-8be0-c24514920b3e";

		AtlasClassification sensitiveDataClassification = new AtlasClassification();
		sensitiveDataClassification.setTypeName("sensitive_data");
		sensitiveDataClassification.setPropagate(false);
		// This is a mandatory attribute for custom classification sensitive_data
		sensitiveDataClassification.setAttribute("durationInDays", 10);

		AtlasClassification classifiedClassification = new AtlasClassification();
		classifiedClassification.setTypeName("classified");
		classifiedClassification.setPropagate(false);

		atlasClient.addClassifications(entityGuid,
				Arrays.asList(sensitiveDataClassification, classifiedClassification));

	}

}