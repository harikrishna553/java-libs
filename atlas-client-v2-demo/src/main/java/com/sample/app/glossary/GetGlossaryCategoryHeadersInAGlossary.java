package com.sample.app.glossary;

import java.util.List;

import org.apache.atlas.AtlasClientV2;
import org.apache.atlas.AtlasServiceException;
import org.apache.atlas.model.glossary.relations.AtlasRelatedCategoryHeader;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sample.app.util.JsonUtil;

public class GetGlossaryCategoryHeadersInAGlossary {

	public static void main(String[] args) throws AtlasServiceException, JsonProcessingException {
		AtlasClientV2 atlasClient = new AtlasClientV2(new String[] { "http://localhost:21000" },
				new String[] { "admin", "admin" });

		String glossaryId = "844a3e1a-110c-4bef-91bf-622dc1eab3e7";

		List<AtlasRelatedCategoryHeader> glossaryCategoryHeaders = atlasClient.getGlossaryCategoryHeaders(glossaryId, null, 10, 0);

		if (glossaryCategoryHeaders == null) {
			System.out.println("Glossary categories not exists");
		} else {
			System.out.println("Glossary categories : \n" + JsonUtil.prettyPrintJson(glossaryCategoryHeaders));
		}

	}

}
