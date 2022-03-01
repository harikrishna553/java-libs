package com.sample.app.glossary;

import java.util.List;

import org.apache.atlas.AtlasClientV2;
import org.apache.atlas.AtlasServiceException;
import org.apache.atlas.model.glossary.AtlasGlossary;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sample.app.util.JsonUtil;

public class GetAllGlossaries {
	public static void main(String[] args) throws AtlasServiceException, JsonProcessingException {
		AtlasClientV2 atlasClient = new AtlasClientV2(new String[] { "http://localhost:21000" },
				new String[] { "admin", "admin" });

		List<AtlasGlossary> glossaries = atlasClient.getAllGlossaries(null, 10, 0);
		
		System.out.println("Glossaires : \n" + JsonUtil.prettyPrintJson(glossaries));
	}
}
