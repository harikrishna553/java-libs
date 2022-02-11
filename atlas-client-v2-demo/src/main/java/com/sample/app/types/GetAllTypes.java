package com.sample.app.types;

import java.util.List;

import org.apache.atlas.AtlasClientV2;
import org.apache.atlas.AtlasServiceException;
import org.apache.atlas.model.SearchFilter;
import org.apache.atlas.model.typedef.AtlasBaseTypeDef;
import org.apache.atlas.model.typedef.AtlasBusinessMetadataDef;
import org.apache.atlas.model.typedef.AtlasClassificationDef;
import org.apache.atlas.model.typedef.AtlasEntityDef;
import org.apache.atlas.model.typedef.AtlasEnumDef;
import org.apache.atlas.model.typedef.AtlasRelationshipDef;
import org.apache.atlas.model.typedef.AtlasStructDef;
import org.apache.atlas.model.typedef.AtlasTypesDef;

import com.fasterxml.jackson.core.JsonProcessingException;

public class GetAllTypes {

	private static <T extends AtlasBaseTypeDef> void printTypeDefs(List<T> typeDefs, String message) {

		System.out.println("__________________________________________________");
		System.out.println(message);
		System.out.println("Typename \t guid \t\t\t\t\t createdBy");
		for (AtlasBaseTypeDef atlasBaseTypeDef : typeDefs) {
			String typeName = atlasBaseTypeDef.getName();
			String guid = atlasBaseTypeDef.getGuid();
			String createdBy = atlasBaseTypeDef.getCreatedBy();

			System.out.println(typeName + "\t" + guid + "\t" + createdBy);

		}

		System.out.println("\n\n");

	}

	public static void main(String[] args) throws AtlasServiceException, JsonProcessingException {

		AtlasClientV2 atlasClient = new AtlasClientV2(new String[] { "http://localhost:21000" },
				new String[] { "admin", "admin" });

		SearchFilter searchFilter = new SearchFilter();
		AtlasTypesDef atlasTypesDef = atlasClient.getAllTypeDefs(searchFilter);

		List<AtlasBusinessMetadataDef> businessMetadataDefs = atlasTypesDef.getBusinessMetadataDefs();
		printTypeDefs(businessMetadataDefs, "businessMetadataDefs");

		List<AtlasClassificationDef> classificationDefs = atlasTypesDef.getClassificationDefs();
		printTypeDefs(classificationDefs, "classificationDefs");

		List<AtlasEntityDef> entityDefs = atlasTypesDef.getEntityDefs();
		printTypeDefs(entityDefs, "entityDefs");

		List<AtlasEnumDef> enumDefs = atlasTypesDef.getEnumDefs();
		printTypeDefs(enumDefs, "enumDefs");

		List<AtlasRelationshipDef> relationshipDefs = atlasTypesDef.getRelationshipDefs();
		printTypeDefs(relationshipDefs, "relationshipDefs");

		List<AtlasStructDef> structDefs = atlasTypesDef.getStructDefs();
		printTypeDefs(structDefs, "structDefs");

	}
}