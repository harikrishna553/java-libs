package com.sample.app.server;

import org.apache.atlas.AtlasClientV2;
import org.apache.atlas.AtlasServiceException;

public class ServerReadyCheck {
	public static void main(String[] args) throws AtlasServiceException {
		AtlasClientV2 atlasClient = new AtlasClientV2(new String[] { "http://localhost:21000" },
				new String[] { "admin", "admin" });

		if (atlasClient.isServerReady()) {
			System.out.println("Server is ready to accept requests");
		} else {
			System.out.println("Server is ready");
		}

	}
}
