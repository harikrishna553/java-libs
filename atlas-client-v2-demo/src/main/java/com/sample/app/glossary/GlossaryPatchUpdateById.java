package com.sample.app.glossary;

import java.util.HashMap;
import java.util.Map;

import org.apache.atlas.AtlasClientV2;
import org.apache.atlas.AtlasServiceException;
import org.apache.atlas.model.glossary.AtlasGlossary;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sample.app.util.JsonUtil;

public class GlossaryPatchUpdateById {

	public static void main(String[] args) throws AtlasServiceException, JsonProcessingException {
		AtlasClientV2 atlasClient = new AtlasClientV2(new String[] { "http://localhost:21000" },
				new String[] { "admin", "admin" });

		String glossaryId = "844a3e1a-110c-4bef-91bf-622dc1eab3e7";
		AtlasGlossary atlasGlossary = atlasClient.getGlossaryByGuid(glossaryId);
		System.out.println("persisted glossary : " + JsonUtil.prettyPrintJson(atlasGlossary));

		if (atlasGlossary == null) {
			System.out.println("Glossary not exists");
			System.exit(0);
		}

		Map<String, String> attributes = new HashMap<>();
		attributes.put("name", atlasGlossary.getName() + "_updated");

		atlasGlossary = atlasClient.partialUpdateGlossaryByGuid(glossaryId, attributes);

		System.out.println("Updated glossary : \n" + JsonUtil.prettyPrintJson(atlasGlossary));

	}

}
