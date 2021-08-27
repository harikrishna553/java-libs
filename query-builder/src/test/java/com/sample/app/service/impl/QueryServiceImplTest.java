package com.sample.app.service.impl;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;

import com.sample.app.dto.SqlQueryRequestDto;
import com.sample.app.exception.TransformationPayloadException;
import com.sample.app.util.JsonUtil;

/**
 * {

	"merges": [{
			"sourceFields": [
				"DOMAIN_NM",
				"DOMAIN_DISPLAY_NM"
			],
			"delimter": ",",
			"newFieldName": "name"
		},

		{
			"sourceFields": [
				"DOMAIN_DESC",
				"DOMAIN_DISPLAY_NM"
			],
			"delimter": ",",
			"newFieldName": "desc_name"
		}
	],

	"lowers": [{
		"sourceField": "DOMAIN_NM",
		"newFieldName": "DOMAIN_NM_LOWER"
	}],

	"reverse": [{
		"sourceField": "DOMAIN_NM",
		"newFieldName": "DOMAIN_NM_REVERSE"
	}]
}
 * @author h0g01ex
 *
 */
public class QueryServiceImplTest {

	@Test
	public void test1() throws TransformationPayloadException {
		SqlQueryRequestDto dto = new SqlQueryRequestDto();
		dto.setTableName("DOMAIN");
		/*String json  = "{\n"
				+ "   \"merges\":[\n"
				+ "      {\n"
				+ "         \"sourceFields\":[\n"
				+ "            \"week_start_date\",\n"
				+ "            \"review_date\"\n"
				+ "         ],\n"
				+ "         \"delimter\":\",\",\n"
				+ "         \"newFieldName\":\"sr_date\"\n"
				+ "      }\n"
				+ "   ]\n"
				+ "}";*/
		
		String json = "{\n"
				+ "\n"
				+ "	\"merges\": [{\n"
				+ "			\"sourceFields\": [\n"
				+ "				\"DOMAIN_NM\",\n"
				+ "				\"DOMAIN_DISPLAY_NM\"\n"
				+ "			],\n"
				+ "			\"delimter\": \",\",\n"
				+ "			\"newFieldName\": \"name\"\n"
				+ "		},\n"
				+ "\n"
				+ "		{\n"
				+ "			\"sourceFields\": [\n"
				+ "				\"DOMAIN_DESC\",\n"
				+ "				\"DOMAIN_DISPLAY_NM\"\n"
				+ "			],\n"
				+ "			\"delimter\": \",\",\n"
				+ "			\"newFieldName\": \"desc_name\"\n"
				+ "		}\n"
				+ "	]\n"
				+ "}";
		
		Map<String, Object> map = JsonUtil.map(json);
		
		dto.setTransformations(map);
		
		QueryServiceImpl queryServiceImpl = new QueryServiceImpl();
		String query = queryServiceImpl.generateQuery(dto);
		//System.out.println(query);
		String expected = "SELECT CONCAT(DOMAIN_NM,',',DOMAIN_DISPLAY_NM) AS name,CONCAT(DOMAIN_DESC,',',DOMAIN_DISPLAY_NM) AS desc_name FROM DOMAIN";
		
		assertEquals(expected, query);
	
	}
	
	@Test
	public void test2() throws TransformationPayloadException {
		SqlQueryRequestDto dto = new SqlQueryRequestDto();
		dto.setTableName("DOMAIN");
	
		
		String json ="{\n"
				+ "\n"
				+ "	\"merges\": [{\n"
				+ "			\"sourceFields\": [\n"
				+ "				\"DOMAIN_NM\",\n"
				+ "				\"DOMAIN_DISPLAY_NM\"\n"
				+ "			],\n"
				+ "			\"delimter\": \",\",\n"
				+ "			\"newFieldName\": \"name\"\n"
				+ "		},\n"
				+ "\n"
				+ "		{\n"
				+ "			\"sourceFields\": [\n"
				+ "				\"DOMAIN_DESC\",\n"
				+ "				\"DOMAIN_DISPLAY_NM\"\n"
				+ "			],\n"
				+ "			\"delimter\": \",\",\n"
				+ "			\"newFieldName\": \"desc_name\"\n"
				+ "		}\n"
				+ "	],\n"
				+ "\n"
				+ "	\"lowers\": [{\n"
				+ "		\"sourceField\": \"DOMAIN_NM\",\n"
				+ "		\"newFieldName\": \"DOMAIN_NM_LOWER\"\n"
				+ "	}]\n"
				+ "}";
		
		Map<String, Object> map = JsonUtil.map(json);
		
		dto.setTransformations(map);
		
		QueryServiceImpl queryServiceImpl = new QueryServiceImpl();
		String query = queryServiceImpl.generateQuery(dto);
		//System.out.println(query);
		String expected = "SELECT CONCAT(DOMAIN_NM,',',DOMAIN_DISPLAY_NM) AS name,CONCAT(DOMAIN_DESC,',',DOMAIN_DISPLAY_NM) AS desc_name,LOWER(DOMAIN_NM) AS DOMAIN_NM_LOWER FROM DOMAIN";
		assertEquals(expected, query);
	}
	
	@Test
	public void test3() throws TransformationPayloadException {
		SqlQueryRequestDto dto = new SqlQueryRequestDto();
		dto.setTableName("DOMAIN");
	
		
		String json ="{\n"
				+ "\n"
				+ "	\"merges\": [{\n"
				+ "			\"sourceFields\": [\n"
				+ "				\"DOMAIN_NM\",\n"
				+ "				\"DOMAIN_DISPLAY_NM\"\n"
				+ "			],\n"
				+ "			\"delimter\": \",\",\n"
				+ "			\"newFieldName\": \"name\"\n"
				+ "		},\n"
				+ "\n"
				+ "		{\n"
				+ "			\"sourceFields\": [\n"
				+ "				\"DOMAIN_DESC\",\n"
				+ "				\"DOMAIN_DISPLAY_NM\"\n"
				+ "			],\n"
				+ "			\"delimter\": \",\",\n"
				+ "			\"newFieldName\": \"desc_name\"\n"
				+ "		}\n"
				+ "	],\n"
				+ "\n"
				+ "	\"lowers\": [{\n"
				+ "		\"sourceField\": \"DOMAIN_NM\",\n"
				+ "		\"newFieldName\": \"DOMAIN_NM_LOWER\"\n"
				+ "	}],\n"
				+ "\n"
				+ "	\"reverse\": [{\n"
				+ "		\"sourceField\": \"DOMAIN_NM\",\n"
				+ "		\"newFieldName\": \"DOMAIN_NM_REVERSE\"\n"
				+ "	}]\n"
				+ "}";
		
		Map<String, Object> map = JsonUtil.map(json);
		
		dto.setTransformations(map);
		
		QueryServiceImpl queryServiceImpl = new QueryServiceImpl();
		String query = queryServiceImpl.generateQuery(dto);
		//System.out.println(query);
		String expected = "SELECT CONCAT(DOMAIN_NM,',',DOMAIN_DISPLAY_NM) AS name,CONCAT(DOMAIN_DESC,',',DOMAIN_DISPLAY_NM) AS desc_name,LOWER(DOMAIN_NM) AS DOMAIN_NM_LOWER,REVERSE(DOMAIN_NM) AS DOMAIN_NM_REVERSE FROM DOMAIN";
		assertEquals(expected, query);
	}
}
