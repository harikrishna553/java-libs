package com.sample.app.glossary;

import java.util.List;

import org.apache.atlas.AtlasClientV2;
import org.apache.atlas.AtlasServiceException;
import org.apache.atlas.model.glossary.relations.AtlasRelatedTermHeader;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sample.app.util.JsonUtil;

public class TermsInACategory {

	public static void main(String[] args) throws AtlasServiceException, JsonProcessingException {
		AtlasClientV2 atlasClient = new AtlasClientV2(new String[] { "http://localhost:21000" },
				new String[] { "admin", "admin" });

		String categoryId = "80e199fb-f924-4470-adc1-8227b5650f6c";

		List<AtlasRelatedTermHeader> termsInACategory = atlasClient.getCategoryTerms(categoryId, null, 10, 0);

		if (termsInACategory == null) {
			System.out.println("no terms associated with this category");
		} else {
			System.out.println("terms : \n" + JsonUtil.prettyPrintJson(termsInACategory));
		}

	}

}
