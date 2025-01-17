package com.sample.app;

import io.milvus.client.MilvusServiceClient;
import io.milvus.param.ConnectParam;
import io.milvus.param.R;
import io.milvus.param.RpcStatus;
import io.milvus.param.collection.CreateDatabaseParam;
import io.milvus.param.collection.DropDatabaseParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MilvusHelloWorld {

	// Use SLF4J for logging
	private static final Logger LOGGER = LoggerFactory.getLogger(MilvusHelloWorld.class);

	// Utility method to handle and print response from Milvus server
	private static void printResponse(R<RpcStatus> response, String operation) {
		if (response.getStatus() == 0) {
			LOGGER.info("Database {} successfull.....", operation);
			LOGGER.info("Response Status: {}", response.getStatus());
		} else {
			LOGGER.error("Database {} failed.", operation);
			LOGGER.error("Error Status: {}", response.getStatus());
			LOGGER.error("Error Message: {}", response.getMessage());
		}
	}

	public static void main(String[] args) {
		String URI = "http://localhost:19530";
		String TOKEN = "root:Milvus";
		String DATABASE = "demodb";

		MilvusServiceClient client = null;

		try {
			// 1. Connect to Milvus server
			ConnectParam connectParam = ConnectParam.newBuilder().withUri(URI).withToken(TOKEN).build();

			client = new MilvusServiceClient(connectParam);

			// 2. Create a new database
			CreateDatabaseParam createDatabaseParam = CreateDatabaseParam.newBuilder().withDatabaseName(DATABASE)
					.build();

			R<RpcStatus> response = client.createDatabase(createDatabaseParam);
			printResponse(response, "Creation");

			// 3. Drop the database (for testing purposes)
			DropDatabaseParam dropDatabaseParam = DropDatabaseParam.newBuilder().withDatabaseName(DATABASE).build();

			response = client.dropDatabase(dropDatabaseParam);
			printResponse(response, "Deletion");

		} catch (Exception e) {
			LOGGER.error("An error occurred while interacting with Milvus", e);
		} finally {
			// Ensure the Milvus client is properly closed to release resources
			if (client != null) {
				try {
					client.close();
					LOGGER.info("Milvus client connection closed.");
				} catch (Exception e) {
					LOGGER.error("Failed to close the Milvus client", e);
				}
			}
		}
	}
}
