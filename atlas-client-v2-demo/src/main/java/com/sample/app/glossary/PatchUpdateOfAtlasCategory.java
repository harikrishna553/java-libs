package com.sample.app.glossary;

import java.util.HashMap;
import java.util.Map;

import org.apache.atlas.AtlasClientV2;
import org.apache.atlas.AtlasServiceException;
import org.apache.atlas.model.glossary.AtlasGlossaryCategory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sample.app.util.JsonUtil;

public class PatchUpdateOfAtlasCategory {

	public static void main(String[] args) throws AtlasServiceException, JsonProcessingException {
		AtlasClientV2 atlasClient = new AtlasClientV2(new String[] { "http://localhost:21000" },
				new String[] { "admin", "admin" });

		String categoryId = "80e199fb-f924-4470-adc1-8227b5650f6c";
		AtlasGlossaryCategory atlasGlossaryCategory = atlasClient.getGlossaryCategory(categoryId);

		System.out.println("atlasGlossaryCategory : \n" + JsonUtil.prettyPrintJson(atlasGlossaryCategory));

		System.out.println("\nUpdating the long description of atlasGlossaryCategoryn");
		Map<String, String> attributes = new HashMap<>();
		attributes.put("longDescription", "Patch updating this category long descriptions to showcase the demo");

		atlasGlossaryCategory = atlasClient.partialUpdateCategoryByGuid(categoryId, attributes);

		System.out.println("\nAfter updation atlasGlossaryTerm : \n" + JsonUtil.prettyPrintJson(atlasGlossaryCategory));

	}
}
