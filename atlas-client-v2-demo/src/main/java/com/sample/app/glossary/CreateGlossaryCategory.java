package com.sample.app.glossary;

import org.apache.atlas.AtlasClientV2;
import org.apache.atlas.AtlasServiceException;
import org.apache.atlas.model.glossary.AtlasGlossary;
import org.apache.atlas.model.glossary.AtlasGlossaryCategory;
import org.apache.atlas.model.glossary.relations.AtlasGlossaryHeader;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sample.app.util.JsonUtil;

public class CreateGlossaryCategory {

	public static void main(String[] args) throws AtlasServiceException, JsonProcessingException {
		AtlasClientV2 atlasClient = new AtlasClientV2(new String[] { "http://localhost:21000" },
				new String[] { "admin", "admin" });

		AtlasGlossary atlasGlossary = new AtlasGlossary();
		atlasGlossary.setName("myGlosary2");
		atlasGlossary.setShortDescription("Demo Glossary");
		atlasGlossary.setLongDescription("Demo GLossary.......");

		AtlasGlossary persistedGlossary = atlasClient.createGlossary(atlasGlossary);
		System.out.println("Glossary : \n" + JsonUtil.prettyPrintJson(persistedGlossary));

		AtlasGlossaryCategory glossaryCategory = new AtlasGlossaryCategory();
		glossaryCategory.setName("myGlosary2_category1");
		glossaryCategory.setShortDescription("Demo category");
		glossaryCategory.setLongDescription("Demo Category....");

		AtlasGlossaryHeader atlasGlossaryHeader = new AtlasGlossaryHeader();
		atlasGlossaryHeader.setGlossaryGuid(persistedGlossary.getGuid());
		glossaryCategory.setAnchor(atlasGlossaryHeader);
		AtlasGlossaryCategory persistedGlossaryCategory = atlasClient.createGlossaryCategory(glossaryCategory);

		
		System.out.println("Category : \n" + JsonUtil.prettyPrintJson(persistedGlossaryCategory));

	}
}