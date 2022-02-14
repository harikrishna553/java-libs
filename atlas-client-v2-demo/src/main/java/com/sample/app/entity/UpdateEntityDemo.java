package com.sample.app.entity;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.atlas.AtlasClientV2;
import org.apache.atlas.AtlasServiceException;
import org.apache.atlas.model.instance.AtlasEntity;
import org.apache.atlas.model.instance.AtlasEntity.AtlasEntityWithExtInfo;
import org.apache.atlas.model.instance.EntityMutationResponse;
import org.apache.atlas.model.typedef.AtlasEntityDef;
import org.apache.atlas.model.typedef.AtlasStructDef.AtlasAttributeDef;
import org.apache.atlas.model.typedef.AtlasTypesDef;

import com.fasterxml.jackson.core.JsonProcessingException;

public class UpdateEntityDemo {

	private static final String TYPE_NAME = "DemoType8";

	private static void createType(AtlasClientV2 atlasClient) throws AtlasServiceException {
		AtlasAttributeDef attributeDef1 = new AtlasAttributeDef();
		attributeDef1.setName("a");
		attributeDef1.setTypeName("int");
		attributeDef1.setCardinality(AtlasAttributeDef.Cardinality.SINGLE);
		attributeDef1.setIsIndexable(true);
		attributeDef1.setIsUnique(false);

		AtlasAttributeDef attributeDef2 = new AtlasAttributeDef();
		attributeDef2.setName("b");
		attributeDef2.setTypeName("int");
		attributeDef2.setCardinality(AtlasAttributeDef.Cardinality.SINGLE);
		attributeDef2.setIsIndexable(true);
		attributeDef2.setIsUnique(false);

		AtlasEntityDef atlasEntityDef = new AtlasEntityDef();
		atlasEntityDef.setName(TYPE_NAME);
		atlasEntityDef.setCreatedBy("Krishna");
		atlasEntityDef.setUpdatedBy("krishna");
		atlasEntityDef.setDescription("Represent a laptoo specification");
		atlasEntityDef.setAttributeDefs(Arrays.asList(attributeDef1, attributeDef2));

		Set<String> superTypes = new HashSet<>();
		superTypes.add("DataSet");
		atlasEntityDef.setSuperTypes(superTypes);

		AtlasTypesDef atlasTypesDef = new AtlasTypesDef();

		atlasTypesDef.getEntityDefs().add(atlasEntityDef);

		atlasClient.createAtlasTypeDefs(atlasTypesDef);
	}

	private static String createEntity(AtlasClientV2 atlasClient) throws AtlasServiceException {

		AtlasEntity atlasEntity = new AtlasEntity();

		atlasEntity.setTypeName(TYPE_NAME);

		atlasEntity.setAttribute("a", 10);
		atlasEntity.setAttribute("b", 20);

		// Following are the mandatory attributes
		atlasEntity.setAttribute("qualifiedName", "UpdateEntityDemo_DEMO1");
		atlasEntity.setAttribute("name", "UpdateEntityDemo_DEMO1");

		AtlasEntity.AtlasEntityWithExtInfo atlasEntityWithExtInfo = new AtlasEntity.AtlasEntityWithExtInfo();
		atlasEntityWithExtInfo.setEntity(atlasEntity);

		EntityMutationResponse response = atlasClient.createEntity(atlasEntityWithExtInfo);
		return response.getCreatedEntities().get(0).getGuid();

	}

	public static void main(String[] args) throws AtlasServiceException, JsonProcessingException {

		AtlasClientV2 atlasClient = new AtlasClientV2(new String[] { "http://localhost:21000" },
				new String[] { "admin", "admin" });

		createType(atlasClient);
		String guid = createEntity(atlasClient);

		AtlasEntityWithExtInfo atlasEntityWithExtInfo = atlasClient.getEntityByGuid(guid);

		AtlasEntity atlasEntity = atlasEntityWithExtInfo.getEntity();

		atlasEntity.setAttribute("a", 1111);

		atlasClient.updateEntity(atlasEntityWithExtInfo);

	}
}
