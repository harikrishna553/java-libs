package com.sample.app.types;

import org.apache.atlas.AtlasClientV2;
import org.apache.atlas.AtlasServiceException;
import org.apache.atlas.model.typedef.AtlasEntityDef;
import org.apache.atlas.model.typedef.AtlasStructDef.AtlasAttributeDef;
import org.apache.atlas.model.typedef.AtlasTypesDef;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sample.app.util.JsonUtil;

public class UpdateType {
	public static void main(String[] args) throws AtlasServiceException, JsonProcessingException {

		AtlasClientV2 atlasClient = new AtlasClientV2(new String[] { "http://localhost:21000" },
				new String[] { "admin", "admin" });

		AtlasEntityDef atlasEntityDef = atlasClient.getEntityDefByName("tablet");

		AtlasAttributeDef attributeDef1 = new AtlasAttributeDef();
		attributeDef1.setName("configuration");
		attributeDef1.setTypeName("string");
		attributeDef1.setCardinality(AtlasAttributeDef.Cardinality.SINGLE);
		attributeDef1.setIsIndexable(true);
		attributeDef1.setIsUnique(false);
		attributeDef1.setIsOptional(true);

		atlasEntityDef.getAttributeDefs().add(attributeDef1);

		AtlasTypesDef atlasTypesDef = new AtlasTypesDef();

		atlasTypesDef.getEntityDefs().add(atlasEntityDef);

		AtlasTypesDef atlasTypesDefResponse = atlasClient.updateAtlasTypeDefs(atlasTypesDef);

		String json = JsonUtil.prettyPrintJson(atlasTypesDefResponse);
		System.out.println(json);

	}
}
