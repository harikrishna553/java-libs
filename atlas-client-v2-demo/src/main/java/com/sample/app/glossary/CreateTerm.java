package com.sample.app.glossary;

import org.apache.atlas.AtlasClientV2;
import org.apache.atlas.AtlasServiceException;
import org.apache.atlas.model.glossary.AtlasGlossary;
import org.apache.atlas.model.glossary.AtlasGlossaryTerm;
import org.apache.atlas.model.glossary.relations.AtlasGlossaryHeader;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sample.app.util.JsonUtil;

public class CreateTerm {

	public static void main(String[] args) throws AtlasServiceException, JsonProcessingException {
		AtlasClientV2 atlasClient = new AtlasClientV2(new String[] { "http://localhost:21000" },
				new String[] { "admin", "admin" });

		AtlasGlossary atlasGlossary = new AtlasGlossary();
		atlasGlossary.setName("myGlosary3");
		atlasGlossary.setShortDescription("Demo Glossary");
		atlasGlossary.setLongDescription("Demo GLossary.......");

		AtlasGlossary persistedGlossary = atlasClient.createGlossary(atlasGlossary);
		System.out.println("Glossary : \n" + JsonUtil.prettyPrintJson(persistedGlossary));

		AtlasGlossaryTerm glossaryTerm = new AtlasGlossaryTerm();
		glossaryTerm.setName("term1");
		glossaryTerm.setShortDescription("term1...");
		glossaryTerm.setLongDescription("term1...........");

		AtlasGlossaryHeader atlasGlossaryHeader = new AtlasGlossaryHeader();
		atlasGlossaryHeader.setGlossaryGuid(persistedGlossary.getGuid());
		glossaryTerm.setAnchor(atlasGlossaryHeader);

		AtlasGlossaryTerm persistedGLossaryTerm = atlasClient.createGlossaryTerm(glossaryTerm);
		System.out.println("\n\npersistedGLossaryTerm : \n" + JsonUtil.prettyPrintJson(persistedGLossaryTerm));

	}
}