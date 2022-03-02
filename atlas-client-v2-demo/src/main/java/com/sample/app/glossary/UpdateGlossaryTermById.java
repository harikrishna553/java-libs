package com.sample.app.glossary;

import org.apache.atlas.AtlasClientV2;
import org.apache.atlas.AtlasServiceException;
import org.apache.atlas.model.glossary.AtlasGlossaryTerm;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sample.app.util.JsonUtil;

public class UpdateGlossaryTermById {
	public static void main(String[] args) throws AtlasServiceException, JsonProcessingException {
		AtlasClientV2 atlasClient = new AtlasClientV2(new String[] { "http://localhost:21000" },
				new String[] { "admin", "admin" });

		String termId = "cd2dbbc5-0440-477e-a6e6-175eb13e3d46";

		AtlasGlossaryTerm atlasGlossaryTerm = atlasClient.getGlossaryTerm(termId);
		System.out.println("atlasGlossaryTerm : \n" + JsonUtil.prettyPrintJson(atlasGlossaryTerm));

		System.out.println("\nUpdating the name of glossary term\n");
		atlasGlossaryTerm.setName(atlasGlossaryTerm.getName() + "_updated");

		atlasGlossaryTerm = atlasClient.updateGlossaryTermByGuid(termId, atlasGlossaryTerm);
		System.out.println("\natlasGlossaryTerm : \n" + JsonUtil.prettyPrintJson(atlasGlossaryTerm));

	}

}
