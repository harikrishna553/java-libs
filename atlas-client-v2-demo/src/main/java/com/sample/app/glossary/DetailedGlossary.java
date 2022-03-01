package com.sample.app.glossary;

import org.apache.atlas.AtlasClientV2;
import org.apache.atlas.AtlasServiceException;
import org.apache.atlas.model.glossary.AtlasGlossary;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sample.app.util.JsonUtil;

public class DetailedGlossary {
	public static void main(String[] args) throws AtlasServiceException, JsonProcessingException {
		AtlasClientV2 atlasClient = new AtlasClientV2(new String[] { "http://localhost:21000" },
				new String[] { "admin", "admin" });

		AtlasGlossary.AtlasGlossaryExtInfo atlasGlossary = atlasClient.getGlossaryExtInfo("844a3e1a-110c-4bef-91bf-622dc1eab3e7");
		
		if(atlasGlossary == null) {
			System.out.println("Glossary not exists");
		}else {
			System.out.println("atlasGlossary : \n" + JsonUtil.prettyPrintJson(atlasGlossary));
		}
		
	}
}
