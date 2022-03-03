package com.sample.app.glossary;

import org.apache.atlas.AtlasClientV2;
import org.apache.atlas.AtlasServiceException;

public class DeleteTermById {
	
	public static void main(String[] args) throws AtlasServiceException {
		AtlasClientV2 atlasClient = new AtlasClientV2(new String[] { "http://localhost:21000" },
				new String[] { "admin", "admin" });
		
		String termId = "8d0451be-bfe9-497c-aae9-f1892b156c94";
		
		atlasClient.deleteGlossaryTermByGuid(termId);
	}

}
