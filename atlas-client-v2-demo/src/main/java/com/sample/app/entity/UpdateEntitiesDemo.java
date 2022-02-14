package com.sample.app.entity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.atlas.AtlasClientV2;
import org.apache.atlas.AtlasServiceException;
import org.apache.atlas.model.instance.AtlasEntity;
import org.apache.atlas.model.instance.AtlasEntity.AtlasEntitiesWithExtInfo;
import org.apache.atlas.model.instance.AtlasEntity.AtlasEntityWithExtInfo;
import org.apache.atlas.model.typedef.AtlasEntityDef;
import org.apache.atlas.model.typedef.AtlasStructDef.AtlasAttributeDef;
import org.apache.atlas.model.typedef.AtlasTypesDef;

public class UpdateEntitiesDemo {

	private static final String TYPE_NAME = "DemoType9";

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

	private static void createEntities(AtlasClientV2 atlasClient) throws AtlasServiceException {

		AtlasEntity atlasEntity1 = new AtlasEntity();
		atlasEntity1.setTypeName(TYPE_NAME);
		atlasEntity1.setAttribute("a", 10);
		atlasEntity1.setAttribute("b", 20);
		atlasEntity1.setAttribute("qualifiedName", "UpdateEntitiesDemo_DEMO1");
		atlasEntity1.setAttribute("name", "UpdateEntitiesDemo_DEMO1");

		AtlasEntity atlasEntity2 = new AtlasEntity();
		atlasEntity2.setTypeName(TYPE_NAME);
		atlasEntity2.setAttribute("a", 30);
		atlasEntity2.setAttribute("b", 40);
		atlasEntity2.setAttribute("qualifiedName", "UpdateEntitiesDemo_DEMO2");
		atlasEntity2.setAttribute("name", "UpdateEntitiesDemo_DEMO2");

		AtlasEntity.AtlasEntitiesWithExtInfo entitesToCreate = new AtlasEntity.AtlasEntitiesWithExtInfo();
		entitesToCreate.setEntities(Arrays.asList(atlasEntity1, atlasEntity2));
		atlasClient.createEntities(entitesToCreate);

	}

	public static void main(String[] args) throws AtlasServiceException, IOException {

		AtlasClientV2 atlasClient = new AtlasClientV2(new String[] { "http://localhost:21000" },
				new String[] { "admin", "admin" });

		createType(atlasClient);
		createEntities(atlasClient);

		Map<String, String> entity1UniqueAttributes = new HashMap<>();
		entity1UniqueAttributes.put("qualifiedName", "UpdateEntitiesDemo_DEMO1");
		AtlasEntityWithExtInfo atlasEntityWithExtInfo1 = atlasClient.getEntityByAttribute(TYPE_NAME,
				entity1UniqueAttributes);
		AtlasEntity atlasEntity1 = atlasEntityWithExtInfo1.getEntity();
		atlasEntity1.setAttribute("a", 11111);

		Map<String, String> entity2UniqueAttributes = new HashMap<>();
		entity2UniqueAttributes.put("qualifiedName", "UpdateEntitiesDemo_DEMO2");
		AtlasEntityWithExtInfo atlasEntityWithExtInfo2 = atlasClient.getEntityByAttribute(TYPE_NAME,
				entity2UniqueAttributes);
		AtlasEntity atlasEntity2 = atlasEntityWithExtInfo2.getEntity();
		atlasEntity2.setAttribute("b", 11111);

		AtlasEntitiesWithExtInfo entitiesToUpdate = new AtlasEntitiesWithExtInfo();
		entitiesToUpdate.setEntities(Arrays.asList(atlasEntity1, atlasEntity2));

		System.out.println(
				"Please enter any input to update the entities. Before this, you can go and validate the created entities of type "
						+ TYPE_NAME);
		readInput();

		atlasClient.updateEntities(entitiesToUpdate);

	}

	private static void readInput() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		br.readLine();
	}
}
