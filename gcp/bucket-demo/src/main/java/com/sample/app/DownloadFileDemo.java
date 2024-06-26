package com.sample.app;

import com.sample.app.util.BucketUtil;

public class DownloadFileDemo {

	public static void main(String[] args) throws Exception {

		// Replace with your bucket name
		String bucketName = "";

		// Replace with the path to your service account key file
		String credentialsPath = "";

		BucketUtil bucketUtil = new BucketUtil(bucketName, credentialsPath);

		String filePath = "images/personal/demo.png";
		String destinationPath = "/Users/krishna/Desktop/demo.png";
		
		bucketUtil.downloadFile(filePath, destinationPath);
	}

}
