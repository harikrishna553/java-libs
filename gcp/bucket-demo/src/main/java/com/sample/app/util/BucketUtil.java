package com.sample.app.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.Channels;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import com.google.api.gax.paging.Page;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.ReadChannel;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.Storage.BlobListOption;
import com.google.cloud.storage.StorageException;
import com.google.cloud.storage.StorageOptions;

public class BucketUtil {

	private String bucketName;

	private GoogleCredentials googleCredentials;
	private Storage storage;

	public BucketUtil(String bucketName, String credentialsPath) throws FileNotFoundException, IOException {
		this.bucketName = bucketName;

		// Load the credentials from the service account key file
		googleCredentials = GoogleCredentials.fromStream(new FileInputStream(credentialsPath));

		// Initialize the GCS client with the credentials
		storage = StorageOptions.newBuilder().setCredentials(googleCredentials).build().getService();

	}

	public Blob createFolder(final String folderName) {
		StringUtil.checkNotBlank(folderName, "folderName");
		final String folder = folderName + "/";

		final BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, folder).build();
		return storage.create(blobInfo);

	}

	public Blob uploadFile(String fileName, InputStream inputStream) throws IOException {
		// Create a BlobInfo object
		BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, fileName).build();

		// Upload the file to GCS using the InputStream
		return storage.createFrom(blobInfo, inputStream);
	}

	public void moveFolder(String oldFolderPath, String newFolderPath) {
		// List all blobs in the old folder
		Iterable<Blob> blobs = storage.list(bucketName, Storage.BlobListOption.prefix(oldFolderPath)).iterateAll();

		// List to hold blobs to delete after copying
		List<BlobId> blobsToDelete = new ArrayList<>();

		for (Blob blob : blobs) {
			// Compute the new blob name
			String newBlobName = newFolderPath + blob.getName().substring(oldFolderPath.length());

			// Copy the blob to the new location
			BlobId sourceBlobId = blob.getBlobId();
			BlobId targetBlobId = BlobId.of(bucketName, newBlobName);
			BlobInfo targetBlobInfo = BlobInfo.newBuilder(targetBlobId).build();

			storage.copy(Storage.CopyRequest.of(sourceBlobId, targetBlobInfo));

			// Add the old blob to the delete list
			blobsToDelete.add(sourceBlobId);
		}

		// Delete the old blobs
		for (BlobId blobId : blobsToDelete) {
			storage.delete(blobId);
		}

	}

	public void downloadFile(String filePath, String destinationPath) throws IOException {
		// Get the Blob from GCS
		Blob blob = storage.get(BlobId.of(bucketName, filePath));

		if (blob == null) {
			throw new IOException("No such object exists in the bucket");
		}

		// Create the destination file
		File file = new File(destinationPath);
		file.getParentFile().mkdirs();
		file.createNewFile();

		// Write the content to the destination file
		try (ReadChannel reader = blob.reader();
				InputStream inputStream = Channels.newInputStream(reader);
				OutputStream outputStream = new FileOutputStream(file)) {
			byte[] buffer = new byte[1024];
			int bytesRead;
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}
		}
	}

	public List<String> listFiles(String folderPath) {
		// List the blobs in the specified folder
		Page<Blob> blobs = storage.list(bucketName, BlobListOption.prefix(folderPath),
				BlobListOption.currentDirectory());
		List<String> fileNames = new ArrayList<>();

		for (Blob blob : blobs.iterateAll()) {
			fileNames.add(FileUtil.getFileName(blob.getName()));
		}

		return fileNames;
	}

	public void checkBucketAccess() {
		try {
			// Attempt to get the bucket's metadata
			Bucket bucket = storage.get(bucketName);
			if (bucket != null) {
				System.out.println("Access granted to bucket: " + bucketName);
			} else {
				System.out.println("Bucket does not exist: " + bucketName);
			}
		} catch (StorageException e) {
			System.err.println("Access denied to bucket: " + bucketName);
			System.err.println("Error: " + e.getMessage());
		}
	}

	public static void printBlobDetails(Blob blob) {
		// Get the blob name
		String name = blob.getName();
		// Check if the blob is a file or a folder
		boolean isDirectory = name.endsWith("/");
		String type = isDirectory ? "Folder" : "File";

		// Get other details
		long size = blob.getSize();
		String contentType = blob.getContentType();
		OffsetDateTime createTime = blob.getCreateTimeOffsetDateTime();
		OffsetDateTime updateTime = blob.getUpdateTimeOffsetDateTime();
		String storageClass = blob.getStorageClass().name();

		BlobId blobId = blob.getBlobId();

		// Print details
		System.out.println("Name: " + name);
		System.out.println("blobId: " + blobId);
		System.out.println("Type: " + type);
		System.out.println("Size: " + size + " bytes");
		System.out.println("Content Type: " + contentType);
		System.out.println("Created: " + createTime);
		System.out.println("Updated: " + updateTime);
		System.out.println("Storage Class: " + storageClass);
	}

}

