package com.sample.app.classifications;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.atlas.AtlasClientV2;
import org.apache.atlas.AtlasServiceException;
import org.apache.atlas.model.instance.AtlasClassification;
import org.apache.atlas.model.instance.ClassificationAssociateRequest;

public class AddClassificationToAnEntityByUniqueAttribute {

	public static void main(String[] args) throws AtlasServiceException {
		AtlasClientV2 atlasClient = new AtlasClientV2(new String[] { "http://localhost:21000" },
				new String[] { "admin", "admin" });

		ClassificationAssociateRequest classificationAssociateRequest = new ClassificationAssociateRequest();
		classificationAssociateRequest.setEntityTypeName("DemoType2");

		Map<String, Object> uniqAttributes = new HashMap<>();
		uniqAttributes.put("qualifiedName", "AttributeWithListOfStrings_DEMO1");
		classificationAssociateRequest.setEntitiesUniqueAttributes(Arrays.asList(uniqAttributes));

		AtlasClassification atlasClassification = new AtlasClassification();
		atlasClassification.setTypeName("sensitive_data");
		atlasClassification.setPropagate(false);

		// This is a mandatory attribute for custom classification sensitive_data
		atlasClassification.setAttribute("durationInDays", 10);
		classificationAssociateRequest.setClassification(atlasClassification);

		atlasClient.addClassification(classificationAssociateRequest);

	}

}