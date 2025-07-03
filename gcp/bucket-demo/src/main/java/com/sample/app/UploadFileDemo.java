package com.sample.app;

import java.io.FileInputStream;
import java.io.InputStream;

import com.google.cloud.storage.Blob;
import com.sample.app.util.BucketUtil;

public class UploadFileDemo {

	public static void main(String[] args) throws Exception {

		// Replace with your bucket name
		String bucketName = "";

		// Replace with the path to your service account key file
		String credentialsPath = "";

		BucketUtil bucketUtil = new BucketUtil(bucketName, credentialsPath);

		String localFilePath = "/Users/krishna/Desktop/demo.png";
		Blob blob = null;
		try (InputStream inputStream = new FileInputStream(localFilePath)) {
			blob = bucketUtil.uploadFile("images/personal/demo.png", inputStream);
		}

		bucketUtil.printBlobDetails(blob);

	}

}
