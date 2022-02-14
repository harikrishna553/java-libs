package com.sample.app.classifications;

import org.apache.atlas.AtlasClientV2;
import org.apache.atlas.AtlasServiceException;
import org.apache.atlas.model.typedef.AtlasClassificationDef;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sample.app.util.JsonUtil;

public class GetTheClassificationByName {

	public static void main(String[] args) throws AtlasServiceException, JsonProcessingException {
		AtlasClientV2 atlasClient = new AtlasClientV2(new String[] { "http://localhost:21000" },
				new String[] { "admin", "admin" });

		AtlasClassificationDef atlasClassificationDef = atlasClient.getClassificationDefByName("sensitive_data");

		System.out.println(JsonUtil.prettyPrintJson(atlasClassificationDef));
	}

}
