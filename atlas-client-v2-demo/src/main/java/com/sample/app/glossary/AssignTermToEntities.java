package com.sample.app.glossary;

import java.util.Arrays;
import java.util.List;

import org.apache.atlas.AtlasClientV2;
import org.apache.atlas.AtlasServiceException;
import org.apache.atlas.model.instance.AtlasRelatedObjectId;

public class AssignTermToEntities {
	public static void main(String[] args) throws AtlasServiceException {
		AtlasClientV2 atlasClient = new AtlasClientV2(new String[] { "http://localhost:21000" },
				new String[] { "admin", "admin" });

		String termId = "cd2dbbc5-0440-477e-a6e6-175eb13e3d46";

		String objectId = "4eea7a7e-c59b-46ee-b6e9-a998251dada2";
		String typeName = "DemoType16";

		AtlasRelatedObjectId atlasRelatedObjectId = new AtlasRelatedObjectId();
		atlasRelatedObjectId.setGuid(objectId);
		atlasRelatedObjectId.setTypeName(typeName);

		List<AtlasRelatedObjectId> relatedObjectIds = Arrays.asList(atlasRelatedObjectId);
		
		atlasClient.assignTermToEntities(termId, relatedObjectIds);
	}
}