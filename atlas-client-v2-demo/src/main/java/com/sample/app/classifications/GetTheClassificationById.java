package com.sample.app.classifications;

import org.apache.atlas.AtlasClientV2;
import org.apache.atlas.AtlasServiceException;
import org.apache.atlas.model.typedef.AtlasClassificationDef;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sample.app.util.JsonUtil;

public class GetTheClassificationById {

	public static void main(String[] args) throws AtlasServiceException, JsonProcessingException {
		AtlasClientV2 atlasClient = new AtlasClientV2(new String[] { "http://localhost:21000" },
				new String[] { "admin", "admin" });

		AtlasClassificationDef atlasClassificationDef = atlasClient
				.getClassificationDefByGuid("d71e4578-3e55-4a86-ae04-c9c971ee0420");

		if (atlasClassificationDef == null) {
			System.out.println("No classification found");
			System.exit(1);
		}
		System.out.println(JsonUtil.prettyPrintJson(atlasClassificationDef));
	}

}
