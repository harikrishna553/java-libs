package com.sample.loghash.storage;

import java.io.Closeable;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;

import com.sample.loghash.util.DateUtil;
import com.sample.loghash.util.FileUtil;
import com.sample.loghash.util.JsonUtil;

/**
 * Keep in mind that concurrent access to the same file by multiple threads or
 * processes may lead to unexpected behavior, so proper synchronization
 * mechanisms should be used if necessary.
 * 
 * It do not use separate file to recover the offsets, it reads entire file
 * content and recover.
 */
public class StorageEngineRecoverFromFile implements Closeable {
	private Map<String, Long> offsetMapForKeys;

	private String contentFilePath;
	private RandomAccessFile randomAccessFile;
	private boolean compact_operation_in_progress = false;
	private final String deletePlaceholder;

	public static StorageEngineRecoverFromFile of(String contentFilePath, String deletePlaceholder) throws IOException {
		return new StorageEngineRecoverFromFile(contentFilePath, deletePlaceholder);
	}

	private StorageEngineRecoverFromFile(String contentFilePath, String deletePlaceholder) throws IOException {
		FileUtil.createFileIfNotExists(contentFilePath);

		this.contentFilePath = contentFilePath;

		randomAccessFile = new RandomAccessFile(contentFilePath, "rw");
		populateOffsetMapForKeys();

		this.deletePlaceholder = deletePlaceholder;
		System.out.println("***************** Storage Engine is ready to use ***************** ");
	}

	private void populateOffsetMapForKeys() throws IOException {
		offsetMapForKeys = new HashMap<>();

		try {
			long offsetToRead = randomAccessFile.getFilePointer();

			while (true) {
				randomAccessFile.seek(offsetToRead);
				String key = randomAccessFile.readUTF();
				String value = randomAccessFile.readUTF();

				if (value != null) {
					offsetMapForKeys.put(key, offsetToRead);
				}

				offsetToRead = randomAccessFile.getFilePointer();

			}

		} catch (EOFException e) {
			System.out.println("Populated the offset map");
		}

	}

	private void checkForCompactOperation() throws IOException {

		if (compact_operation_in_progress) {
			throw new IOException("Compact operations is in progress");
		}
	}

	private void write(String key, String value) throws IOException {
		// Move the file pointer to the end of the file
		long offset = randomAccessFile.length();
		randomAccessFile.seek(offset);

		randomAccessFile.writeUTF(key);
		randomAccessFile.writeUTF(value);

		if (this.deletePlaceholder.equals(value)) {
			offsetMapForKeys.remove(key);
		} else {
			offsetMapForKeys.put(key, offset);
		}
	}

	private void write(Map<String, String> map) throws IOException {

		for (Map.Entry<String, String> entry : map.entrySet()) {
			write(entry.getKey(), entry.getValue());
		}

	}

	private String read(String key) throws IOException {
		Long offset = offsetMapForKeys.get(key);
		if (offset == null) {
			return null;
		}

		randomAccessFile.seek(offset);

		// First UTF represents the key
		randomAccessFile.readUTF();

		String value = randomAccessFile.readUTF();

		if (deletePlaceholder.equals(value)) {
			return null;
		}
		return value;
	}

	public void writeKey(String key, String value) throws IOException {
		checkForCompactOperation();
		write(key, value);
	}

	public void writeKeys(Map<String, String> map) throws IOException {

		for (Map.Entry<String, String> entry : map.entrySet()) {
			writeKey(entry.getKey(), entry.getValue());
		}

	}

	public void delete(String key) throws IOException {
		writeKey(key, deletePlaceholder);
	}

	public String readKey(String key) throws IOException {
		checkForCompactOperation();
		return read(key);
	}

	public Map<String, String> readKeys() throws IOException {
		Map<String, String> map = new HashMap<>();
		for (String key : offsetMapForKeys.keySet()) {
			String value = readKey(key);
			if (value != null) {
				map.put(key, value);
			}

		}

		return map;
	}

	/**
	 * Drop all the stale entries
	 * 
	 * @throws IOException
	 */
	public void compact() throws IOException {

		try {
			Map<String, String> activeEntries = readKeys();

			compact_operation_in_progress = true;

			// Close the file Handle
			close();

			// Take backup of old one and write new one
			String timestamp = DateUtil.getTimestamp();
			String parentDirPath = FileUtil.getParentDirectory(contentFilePath);
			String backupContentFilePath = FileUtil.getFileNameWithoutExtension(contentFilePath) + timestamp + "."
					+ FileUtil.getFileExtension(contentFilePath);

			FileUtil.renameFile(contentFilePath, parentDirPath + File.separator + backupContentFilePath);

			FileUtil.createFileIfNotExists(contentFilePath);

			randomAccessFile = new RandomAccessFile(contentFilePath, "rw");
			write(activeEntries);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			compact_operation_in_progress = false;
		}

	}

	@Override
	public void close() {
		String offsetsInfo = JsonUtil.getJson(offsetMapForKeys);
		try {
			randomAccessFile.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}