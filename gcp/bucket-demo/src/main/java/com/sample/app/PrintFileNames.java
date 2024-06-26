package com.sample.app;

import java.util.List;

import com.sample.app.util.BucketUtil;

public class PrintFileNames {

	public static void main(String[] args) throws Exception {

		// Replace with your bucket name
		String bucketName = "";

		// Replace with the path to your service account key file
		String credentialsPath = "";

		BucketUtil bucketUtil = new BucketUtil(bucketName, credentialsPath);

		List<String> fileNames = bucketUtil.listFiles("images/personal/");
		fileNames.stream().forEach(System.out::println);
	}

}
