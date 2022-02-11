package com.sample.app.server;

import java.util.Map;

import org.apache.atlas.AtlasClientV2;
import org.apache.atlas.AtlasServiceException;
import org.apache.atlas.model.metrics.AtlasMetrics;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sample.app.util.JsonUtil;

public class AtlasServerMetrics {
	public static void main(String[] args) throws AtlasServiceException, JsonProcessingException {
		AtlasClientV2 atlasClient = new AtlasClientV2(new String[] { "http://localhost:21000" },
				new String[] { "admin", "admin" });

		AtlasMetrics atlasMetrics = atlasClient.getAtlasMetrics();

		Map<String, Map<String, Object>> metrics = atlasMetrics.getData();
		String json = JsonUtil.prettyPrintJson(metrics);
		System.out.println(json);
		

	}
}