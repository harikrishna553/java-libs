package com.sample.app.glossary;

import org.apache.atlas.AtlasClientV2;
import org.apache.atlas.AtlasServiceException;
import org.apache.atlas.model.glossary.AtlasGlossaryCategory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sample.app.util.JsonUtil;

public class GetGlossaryCategoryById {

	public static void main(String[] args) throws AtlasServiceException, JsonProcessingException {
		AtlasClientV2 atlasClient = new AtlasClientV2(new String[] { "http://localhost:21000" },
				new String[] { "admin", "admin" });

		String categoryId = "80e199fb-f924-4470-adc1-8227b5650f6c";

		AtlasGlossaryCategory atlasGlossaryCategory = atlasClient.getGlossaryCategory(categoryId);

		if (atlasGlossaryCategory == null) {
			System.out.println("Glossary category not exists");
		} else {
			System.out.println("Glossary category : \n" + JsonUtil.prettyPrintJson(atlasGlossaryCategory));
		}

	}

}
