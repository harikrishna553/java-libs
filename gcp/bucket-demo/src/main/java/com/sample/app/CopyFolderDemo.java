package com.sample.app;

import com.sample.app.util.BucketUtil;

public class CopyFolderDemo {
	public static void main(String[] args) throws Exception {

		// Replace with your bucket name
		String bucketName = "";

		// Replace with the path to your service account key file
		String credentialsPath = "";

		BucketUtil bucketUtil = new BucketUtil(bucketName, credentialsPath);
		String oldFolderPath = "images/personal";
		String newFolderPath = "images/personal_renamed";
		bucketUtil.moveFolder(oldFolderPath, newFolderPath);

	}
}
