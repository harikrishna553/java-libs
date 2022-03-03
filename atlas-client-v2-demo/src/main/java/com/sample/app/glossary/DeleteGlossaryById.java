package com.sample.app.glossary;

import org.apache.atlas.AtlasClientV2;
import org.apache.atlas.AtlasServiceException;
import org.apache.atlas.model.glossary.AtlasGlossary;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sample.app.util.JsonUtil;

public class DeleteGlossaryById {

	public static void main(String[] args) throws AtlasServiceException, JsonProcessingException {
		AtlasClientV2 atlasClient = new AtlasClientV2(new String[] { "http://localhost:21000" },
				new String[] { "admin", "admin" });

		String glossaryId = "62bc12e8-90b6-4bcf-bd08-f25bcf37a93b";

		AtlasGlossary atlasGlossary = atlasClient.getGlossaryByGuid(glossaryId);
		System.out.println("atlasGlossary : \n" + JsonUtil.prettyPrintJson(atlasGlossary));

		atlasClient.deleteGlossaryByGuid(glossaryId);

		// This call with throw AtlasServiceException
		atlasClient.getGlossaryByGuid(glossaryId);
	}
}
