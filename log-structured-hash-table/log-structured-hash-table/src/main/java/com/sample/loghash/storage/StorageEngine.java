package com.sample.loghash.storage;

import java.io.Closeable;
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
 */
public class StorageEngine implements Closeable {
	private Map<String, Long> offsetMapForKeys;

	private String contentFilePath;
	private String offsetFilePath;
	private RandomAccessFile randomAccessFile;
	private boolean compact_operation_in_progress = false;
	private final String deletePlaceholder;

	public static StorageEngine of(String contentFilePath, String offsetFilePath, String deletePlaceholder)
			throws IOException {
		return new StorageEngine(contentFilePath, offsetFilePath, deletePlaceholder);
	}

	private StorageEngine(String contentFilePath, String offsetFilePath, String deletePlaceholder) throws IOException {
		FileUtil.createFileIfNotExists(contentFilePath);
		FileUtil.createFileIfNotExists(offsetFilePath);

		this.contentFilePath = contentFilePath;
		this.offsetFilePath = offsetFilePath;

		String offsetFileContent = FileUtil.readFileContentViaReadingAllBytes(offsetFilePath);
		offsetMapForKeys = JsonUtil.offsetMap(offsetFileContent);
		randomAccessFile = new RandomAccessFile(contentFilePath, "rw");
		this.deletePlaceholder = deletePlaceholder;
		System.out.println("***************** Storage Engine is ready to use ***************** ");
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

		if(this.deletePlaceholder.equals(value)) {
			offsetMapForKeys.remove(key);
		}else {
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
			String backupOffsetFilePath = FileUtil.getFileNameWithoutExtension(offsetFilePath) + timestamp + "."
					+ FileUtil.getFileExtension(offsetFilePath);

			FileUtil.renameFile(contentFilePath, parentDirPath + File.separator + backupContentFilePath);
			FileUtil.renameFile(offsetFilePath, parentDirPath + File.separator + backupOffsetFilePath);

			FileUtil.createFileIfNotExists(contentFilePath);
			FileUtil.createFileIfNotExists(offsetFilePath);

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
		FileUtil.write(offsetFilePath, offsetsInfo);
		try {
			randomAccessFile.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

