package com.sample.app.server;

import org.apache.atlas.AtlasClientV2;
import org.apache.atlas.AtlasServiceException;

public class ServiceStatus {
	public static void main(String[] args) throws AtlasServiceException {
		AtlasClientV2 atlasClient = new AtlasClientV2(new String[] { "http://localhost:21000" },
				new String[] { "admin", "admin" });

		String status = atlasClient.getAdminStatus();
		System.out.println(status);
	}
}
