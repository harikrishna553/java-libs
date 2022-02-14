package com.sample.app.classifications;

import java.util.Arrays;

import org.apache.atlas.AtlasClientV2;
import org.apache.atlas.AtlasServiceException;
import org.apache.atlas.model.instance.AtlasClassification;
import org.apache.atlas.model.instance.ClassificationAssociateRequest;

public class AddClassificationToAnEntity {

	public static void main(String[] args) throws AtlasServiceException {
		AtlasClientV2 atlasClient = new AtlasClientV2(new String[] { "http://localhost:21000" },
				new String[] { "admin", "admin" });

		String entityGuid = "898c2aa1-d31a-4d0b-a1fd-7a76da6a4071";

		ClassificationAssociateRequest classificationAssociateRequest = new ClassificationAssociateRequest();
		classificationAssociateRequest.setEntityGuids(Arrays.asList(entityGuid));

		AtlasClassification atlasClassification = new AtlasClassification();
		atlasClassification.setTypeName("sensitive_data");
		atlasClassification.setPropagate(false);

		// This is a mandatory attribute for custom classification sensitive_data
		atlasClassification.setAttribute("durationInDays", 10);
		classificationAssociateRequest.setClassification(atlasClassification);

		atlasClient.addClassification(classificationAssociateRequest);

	}

}