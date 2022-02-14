package com.sample.app.classifications;

import java.util.Arrays;
import java.util.Collections;

import org.apache.atlas.AtlasClientV2;
import org.apache.atlas.AtlasServiceException;
import org.apache.atlas.model.instance.AtlasClassification;

public class UpdateClassificationOfEntityById {

	public static void main(String[] args) throws AtlasServiceException {
		AtlasClientV2 atlasClient = new AtlasClientV2(new String[] { "http://localhost:21000" },
				new String[] { "admin", "admin" });

		String entityGuid = "ff459f6e-8ea4-4e0b-a62b-45dda2247ea8";

		AtlasClassification atlasClassification = new AtlasClassification();
		atlasClassification.setTypeName("sensitive_data");
		atlasClassification.setAttribute("durationInDays", 123);
		atlasClassification.setEntityGuid(entityGuid);
		atlasClassification.setPropagate(false);
		atlasClassification.setRemovePropagationsOnEntityDelete(false);
		atlasClassification.setValidityPeriods(Collections.EMPTY_LIST);

		atlasClient.updateClassifications(entityGuid, Arrays.asList(atlasClassification));

	}

}