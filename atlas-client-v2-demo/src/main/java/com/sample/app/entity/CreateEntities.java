package com.sample.app.entity;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.atlas.AtlasClientV2;
import org.apache.atlas.AtlasServiceException;
import org.apache.atlas.model.instance.AtlasEntity;
import org.apache.atlas.model.typedef.AtlasEntityDef;
import org.apache.atlas.model.typedef.AtlasTypesDef;
import org.apache.atlas.model.typedef.AtlasStructDef.AtlasAttributeDef;

import com.fasterxml.jackson.core.JsonProcessingException;

public class CreateEntities {

	private static final String TYPE_NAME = "DemoType7";

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
		atlasEntityDef.setDescription("Represent a demo specification");
		atlasEntityDef.setAttributeDefs(Arrays.asList(attributeDef1, attributeDef2));

		Set<String> superTypes = new HashSet<>();
		superTypes.add("DataSet");
		atlasEntityDef.setSuperTypes(superTypes);

		AtlasTypesDef atlasTypesDef = new AtlasTypesDef();

		atlasTypesDef.getEntityDefs().add(atlasEntityDef);

		atlasClient.createAtlasTypeDefs(atlasTypesDef);
	}

	private static void createEntity(AtlasClientV2 atlasClient) throws AtlasServiceException {

		// Defining entity1
		AtlasEntity atlasEntity1 = new AtlasEntity();
		atlasEntity1.setTypeName(TYPE_NAME);
		atlasEntity1.setAttribute("a", 10);
		atlasEntity1.setAttribute("b", 20);
		atlasEntity1.setAttribute("qualifiedName", "CreateEntities_DEMO1");
		atlasEntity1.setAttribute("name", "CreateEntities_DEMO1");

		// Defining entity2
		AtlasEntity atlasEntity2 = new AtlasEntity();
		atlasEntity2.setTypeName(TYPE_NAME);
		atlasEntity2.setAttribute("a", 11);
		atlasEntity2.setAttribute("b", 21);
		atlasEntity2.setAttribute("qualifiedName", "CreateEntities_DEMO2");
		atlasEntity2.setAttribute("name", "CreateEntities_DEMO2");

		AtlasEntity.AtlasEntitiesWithExtInfo entitesToCreate = new AtlasEntity.AtlasEntitiesWithExtInfo();
		entitesToCreate.setEntities(Arrays.asList(atlasEntity1, atlasEntity2));
		atlasClient.createEntities(entitesToCreate);
	}

	public static void main(String[] args) throws AtlasServiceException, JsonProcessingException {

		AtlasClientV2 atlasClient = new AtlasClientV2(new String[] { "http://localhost:21000" },
				new String[] { "admin", "admin" });

		createType(atlasClient);
		createEntity(atlasClient);

	}
}
