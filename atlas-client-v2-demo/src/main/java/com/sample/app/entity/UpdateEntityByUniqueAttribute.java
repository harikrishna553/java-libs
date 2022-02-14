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
import org.apache.atlas.model.instance.AtlasEntity.AtlasEntityWithExtInfo;
import org.apache.atlas.model.typedef.AtlasEntityDef;
import org.apache.atlas.model.typedef.AtlasStructDef.AtlasAttributeDef;
import org.apache.atlas.model.typedef.AtlasTypesDef;

public class UpdateEntityByUniqueAttribute {

	private static final String TYPE_NAME = "DemoType13";

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

	private static void createEntity(AtlasClientV2 atlasClient) throws AtlasServiceException {

		AtlasEntity atlasEntity = new AtlasEntity();

		atlasEntity.setTypeName(TYPE_NAME);

		atlasEntity.setAttribute("a", 10);
		atlasEntity.setAttribute("b", 20);

		// Following are the mandatory attributes
		atlasEntity.setAttribute("qualifiedName", "UpdateEntityByUniqueAttribute_DEMO1");
		atlasEntity.setAttribute("name", "UpdateEntityByUniqueAttribute_DEMO1");

		AtlasEntity.AtlasEntityWithExtInfo atlasEntityWithExtInfo = new AtlasEntity.AtlasEntityWithExtInfo();
		atlasEntityWithExtInfo.setEntity(atlasEntity);

		atlasClient.createEntity(atlasEntityWithExtInfo);

	}

	public static void main(String[] args) throws AtlasServiceException, IOException {

		AtlasClientV2 atlasClient = new AtlasClientV2(new String[] { "http://localhost:21000" },
				new String[] { "admin", "admin" });

		createType(atlasClient);
		createEntity(atlasClient);

		System.out.println(
				"Please enter any input to update the entities. Before this, you can go and validate the created entities of type "
						+ TYPE_NAME);
		readInput();

		Map<String, String> uniqAttributes = new HashMap<>();
		uniqAttributes.put("qualifiedName", "UpdateEntityByUniqueAttribute_DEMO1");

		AtlasEntityWithExtInfo atlasEntityWithExtInfo = new AtlasEntityWithExtInfo();
		AtlasEntity atlasEntity = new AtlasEntity();
		atlasEntity.setTypeName(TYPE_NAME);
		atlasEntity.setAttribute("a", 1234567);
		atlasEntity.setAttribute("qualifiedName", "UpdateEntityByUniqueAttribute_DEMO1");
		atlasEntity.setAttribute("name", "UpdateEntityByUniqueAttribute_DEMO1");
		atlasEntity.setAttribute("typeName", TYPE_NAME);
		atlasEntityWithExtInfo.setEntity(atlasEntity);

		atlasClient.updateEntityByAttribute(TYPE_NAME, uniqAttributes, atlasEntityWithExtInfo);

	}

	private static void readInput() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		br.readLine();
	}
}