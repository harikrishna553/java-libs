package com.sample.app.glossary;

import java.util.HashMap;
import java.util.Map;

import org.apache.atlas.AtlasClientV2;
import org.apache.atlas.AtlasServiceException;
import org.apache.atlas.model.glossary.AtlasGlossaryTerm;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sample.app.util.JsonUtil;

public class PatchUpdateGlossaryTermById {

	public static void main(String[] args) throws AtlasServiceException, JsonProcessingException {
		AtlasClientV2 atlasClient = new AtlasClientV2(new String[] { "http://localhost:21000" },
				new String[] { "admin", "admin" });

		String termId = "8d0451be-bfe9-497c-aae9-f1892b156c94";

		AtlasGlossaryTerm atlasGlossaryTerm = atlasClient.getGlossaryTerm(termId);
		System.out.println("atlasGlossaryTerm : \n" + JsonUtil.prettyPrintJson(atlasGlossaryTerm));

		System.out.println("\nUpdating the long description of glossary term\n");
		Map<String, String> attributes = new HashMap<>();
		attributes.put("longDescription", "term2 is used for demo purpose");

		atlasGlossaryTerm = atlasClient.partialUpdateTermByGuid(termId, attributes);
		System.out.println("\natlasGlossaryTerm : \n" + JsonUtil.prettyPrintJson(atlasGlossaryTerm));
		

	}
}
