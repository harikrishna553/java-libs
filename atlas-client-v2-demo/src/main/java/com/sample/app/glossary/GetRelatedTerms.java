package com.sample.app.glossary;

import java.util.Map;
import java.util.Set;

import org.apache.atlas.AtlasClientV2;
import org.apache.atlas.AtlasServiceException;
import org.apache.atlas.model.glossary.AtlasGlossaryTerm;
import org.apache.atlas.model.glossary.relations.AtlasRelatedTermHeader;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sample.app.util.JsonUtil;

public class GetRelatedTerms {

	public static void main(String[] args) throws AtlasServiceException, JsonProcessingException {
		AtlasClientV2 atlasClient = new AtlasClientV2(new String[] { "http://localhost:21000" },
				new String[] { "admin", "admin" });

		String termId = "cd2dbbc5-0440-477e-a6e6-175eb13e3d46";

		Map<AtlasGlossaryTerm.Relation, Set<AtlasRelatedTermHeader>> relatedTerms = atlasClient.getRelatedTerms(termId, null, 10, 0);

		if (relatedTerms == null) {
			System.out.println("Related terms not exists");
		} else {
			System.out.println("Related terms : \n" + JsonUtil.prettyPrintJson(relatedTerms));
		}

	}

}
