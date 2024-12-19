package com.sample.app;

import io.milvus.client.MilvusServiceClient;
import io.milvus.param.ConnectParam;
import io.milvus.param.R;
import io.milvus.param.RpcStatus;
import io.milvus.param.collection.CreateDatabaseParam;

public class MilvusHelloWorld {

	public static void main(String[] args) {
		String URI = "http://localhost:19530";
		String TOKEN = "root:Milvus";

		// 1. Connect to Milvus server
		ConnectParam connectParam = ConnectParam.newBuilder().withUri(URI).withToken(TOKEN).build();

		MilvusServiceClient client = new MilvusServiceClient(connectParam);

		// 2. Create a new database
		CreateDatabaseParam createDatabaseParam = CreateDatabaseParam.newBuilder().withDatabaseName("demodb").build();

		R<RpcStatus> response = client.createDatabase(createDatabaseParam);

		// 3. Print detailed response
		if (response.getStatus() == 0) {
			System.out.println("Database created successfully!");
			System.out.println("Response Status: " + response.getStatus());
		} else {
			System.out.println("Database creation failed.");
			System.out.println("Error Status: " + response.getStatus());
			System.out.println("Error Message: " + response.getMessage());
		}
	}
}
