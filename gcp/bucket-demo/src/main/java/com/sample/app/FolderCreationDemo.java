package com.sample.app;

import com.google.cloud.storage.Blob;
import com.sample.app.util.BucketUtil;

public class FolderCreationDemo {

	public static void main(String[] args) throws Exception {

		// Replace with your bucket name
		String bucketName = "";

		// Replace with the path to your service account key file
		String credentialsPath = "";

		BucketUtil bucketUtil = new BucketUtil(bucketName, credentialsPath);
		String folderName = "images/img1";
		Blob blob = bucketUtil.createFolder(folderName);

		BucketUtil.printBlobDetails(blob);

	}
}
