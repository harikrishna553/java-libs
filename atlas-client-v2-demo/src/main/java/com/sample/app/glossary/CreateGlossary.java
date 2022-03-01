package com.sample.app.glossary;

import org.apache.atlas.AtlasClientV2;
import org.apache.atlas.AtlasServiceException;
import org.apache.atlas.model.glossary.AtlasGlossary;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sample.app.util.JsonUtil;

public class CreateGlossary {

	public static void main(String[] args) throws AtlasServiceException, JsonProcessingException {
		AtlasClientV2 atlasClient = new AtlasClientV2(new String[] { "http://localhost:21000" },
				new String[] { "admin", "admin" });

		AtlasGlossary atlasGlossary = new AtlasGlossary();
		atlasGlossary.setName("myGlosary1");
		atlasGlossary.setShortDescription("Demo Glossary");
		atlasGlossary.setLongDescription("Demo GLossary.......");

		AtlasGlossary persistedGlossary = atlasClient.createGlossary(atlasGlossary);

		System.out.println(JsonUtil.prettyPrintJson(persistedGlossary));

	}
}
