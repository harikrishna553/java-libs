package com.sample.app.glossary;

import java.util.Arrays;
import java.util.Map;

import org.apache.atlas.AtlasClientV2;
import org.apache.atlas.AtlasServiceException;
import org.apache.atlas.model.instance.AtlasEntity.AtlasEntityWithExtInfo;
import org.apache.atlas.model.instance.AtlasRelatedObjectId;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sample.app.util.JsonUtil;

/**
 * We need a relationshipGuid to disassociate a term from the entity.
 * 
 * @author Krishna
 *
 */
public class DetachTermFromAnEntity {
	private static AtlasClientV2 atlasClient = new AtlasClientV2(new String[] { "http://localhost:21000" },
			new String[] { "admin", "admin" });

	public static void detachTermFromAnEntity(String term, String entityId)
			throws JsonProcessingException, AtlasServiceException {
		AtlasEntityWithExtInfo atlasEntityWithExtInfo = atlasClient.getEntityByGuid(entityId);

		Map<String, Object> relationShipAttributes = atlasEntityWithExtInfo.getEntity().getRelationshipAttributes();
		Object meanings = relationShipAttributes.get("meanings");

		String json = JsonUtil.marshal(meanings);

		ObjectMapper mapper = new ObjectMapper();
		JsonNode jsonNode = mapper.readTree(json);

		if (!jsonNode.isArray()) {
			System.exit(0);
		}

		String relationShipGuid = null;
		String termId = null;
		int noOfElements = jsonNode.size();
		for (int i = 0; i < noOfElements; i++) {
			JsonNode node = jsonNode.get(i);

			if (!"AtlasGlossaryTerm".equals(node.get("typeName").asText())) {
				continue;
			}

			if (!term.equals(node.get("displayText").asText())) {
				continue;
			}

			relationShipGuid = node.get("relationshipGuid").asText();
			termId = node.get("guid").asText();

			break;
		}

		AtlasRelatedObjectId atlasRelatedObjectId = new AtlasRelatedObjectId();
		atlasRelatedObjectId.setGuid(entityId);
		atlasRelatedObjectId.setRelationshipGuid(relationShipGuid);

		atlasClient.disassociateTermFromEntities(termId, Arrays.asList(atlasRelatedObjectId));
	}

	public static void main(String[] args) throws AtlasServiceException, JsonProcessingException {

		String termToRemove = "sensitive";

		String entityId = "e63cb343-675e-4cde-9d49-ceb43fb1aacb";

		detachTermFromAnEntity(termToRemove, entityId);

	}
}