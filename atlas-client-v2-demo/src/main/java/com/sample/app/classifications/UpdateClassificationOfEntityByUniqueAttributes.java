package com.sample.app.classifications;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.atlas.AtlasClientV2;
import org.apache.atlas.AtlasServiceException;
import org.apache.atlas.model.instance.AtlasClassification;

public class UpdateClassificationOfEntityByUniqueAttributes {

	public static void main(String[] args) throws AtlasServiceException {
		AtlasClientV2 atlasClient = new AtlasClientV2(new String[] { "http://localhost:21000" },
				new String[] { "admin", "admin" });

		AtlasClassification atlasClassification = new AtlasClassification();
		atlasClassification.setTypeName("sensitive_data");
		atlasClassification.setAttribute("durationInDays", 123);
		atlasClassification.setPropagate(false);
		atlasClassification.setRemovePropagationsOnEntityDelete(false);
		atlasClassification.setValidityPeriods(Collections.EMPTY_LIST);

		Map<String, String> uniqAttributes = new HashMap<>();
		uniqAttributes.put("qualifiedName", "CreateEntity_DEMO1");

		atlasClient.updateClassifications("DemoType1", uniqAttributes, Arrays.asList(atlasClassification));

	}

}