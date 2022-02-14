package com.sample.app.classifications;

import java.util.ArrayList;
import java.util.List;

import org.apache.atlas.AtlasClientV2;
import org.apache.atlas.AtlasServiceException;
import org.apache.atlas.model.typedef.AtlasClassificationDef;
import org.apache.atlas.model.typedef.AtlasStructDef.AtlasAttributeDef;
import org.apache.atlas.model.typedef.AtlasTypesDef;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sample.app.util.JsonUtil;

public class CreateNewClassification {
	public static void main(String[] args) throws AtlasServiceException, JsonProcessingException {
		AtlasClientV2 atlasClient = new AtlasClientV2(new String[] { "http://localhost:21000" },
				new String[] { "admin", "admin" });

		AtlasClassificationDef atlasClassificationDef = new AtlasClassificationDef();
		atlasClassificationDef.setName("sensitive_data");
		atlasClassificationDef.setDescription("Entities with sensitive data can classified as sensitive");
		atlasClassificationDef.setCreatedBy("Krishna");
		atlasClassificationDef.setUpdatedBy("Krishna");

		AtlasAttributeDef attributeDef1 = new AtlasAttributeDef();
		attributeDef1.setName("durationInDays");
		attributeDef1.setTypeName("int");
		attributeDef1.setDefaultValue("12");

		atlasClassificationDef.getAttributeDefs().add(attributeDef1);

		AtlasTypesDef atlasTypesDef = new AtlasTypesDef();
		atlasTypesDef.getClassificationDefs().add(atlasClassificationDef);

		AtlasTypesDef atlasTypesDefResponse = atlasClient.createAtlasTypeDefs(atlasTypesDef);

		String json = JsonUtil.prettyPrintJson(atlasTypesDefResponse);
		System.out.println(json);

	}
}

