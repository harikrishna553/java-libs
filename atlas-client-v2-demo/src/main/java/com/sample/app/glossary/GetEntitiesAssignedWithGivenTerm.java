package com.sample.app.glossary;

import java.util.List;

import org.apache.atlas.AtlasClientV2;
import org.apache.atlas.AtlasServiceException;
import org.apache.atlas.model.instance.AtlasRelatedObjectId;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sample.app.util.JsonUtil;

public class GetEntitiesAssignedWithGivenTerm {

	public static void main(String[] args) throws AtlasServiceException, JsonProcessingException {
		AtlasClientV2 atlasClient = new AtlasClientV2(new String[] { "http://localhost:21000" },
				new String[] { "admin", "admin" });

		String termId = "cd2dbbc5-0440-477e-a6e6-175eb13e3d46";

		List<AtlasRelatedObjectId> entitiesAssignedToThisTerm = atlasClient.getEntitiesAssignedWithTerm(termId, null,
				10, 0);

		System.out.println(JsonUtil.prettyPrintJson(entitiesAssignedToThisTerm));
	}

}