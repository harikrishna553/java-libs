package com.sample.app.attributes;

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

public class AttributeWithSetOfStrings {

	private static final String TYPE_NAME = "DemoType3";

	private static void createType(AtlasClientV2 atlasClient) throws AtlasServiceException {
		AtlasAttributeDef attributeDef1 = new AtlasAttributeDef();
		attributeDef1.setName("hobbies");
		attributeDef1.setTypeName("array<string>");
		attributeDef1.setCardinality(AtlasAttributeDef.Cardinality.SET);
		attributeDef1.setIsIndexable(true);
		attributeDef1.setIsUnique(false);

		AtlasEntityDef atlasEntityDef = new AtlasEntityDef();
		atlasEntityDef.setName(TYPE_NAME);
		atlasEntityDef.setCreatedBy("Krishna");
		atlasEntityDef.setUpdatedBy("krishna");
		atlasEntityDef.setDescription("Represent a laptoo specification");
		atlasEntityDef.setAttributeDefs(Arrays.asList(attributeDef1));

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

		atlasEntity.setAttribute("hobbies", Arrays.asList("football", "cricket", "football"));

		// Following are the mandatory attributes
		atlasEntity.setAttribute("qualifiedName", "AttributeWithSetOfStrings_DEMO1");
		atlasEntity.setAttribute("name", "AttributeWithSetOfStrings_DEMO1");

		AtlasEntity.AtlasEntityWithExtInfo atlasEntityWithExtInfo = new AtlasEntity.AtlasEntityWithExtInfo();
		atlasEntityWithExtInfo.setEntity(atlasEntity);

		atlasClient.createEntity(atlasEntityWithExtInfo);
	}

	public static void main(String[] args) throws AtlasServiceException, JsonProcessingException {

		AtlasClientV2 atlasClient = new AtlasClientV2(new String[] { "http://localhost:21000" },
				new String[] { "admin", "admin" });

		createType(atlasClient);
		createEntity(atlasClient);

	}
}
