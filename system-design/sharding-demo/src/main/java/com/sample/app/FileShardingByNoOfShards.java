package com.sample.app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileShardingByNoOfShards {

	public static void shardFiles(String inputFile, int numShards, String shardFilePrefix) {

		try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
			FileWriter[] writers = new FileWriter[numShards];
			for (int i = 0; i < numShards; i++) {
				writers[i] = new FileWriter(shardFilePrefix + i + ".txt");
			}

			String line;
			int shardIndex = 0;
			while ((line = reader.readLine()) != null) {
				writers[shardIndex].write(line + "\n");
				shardIndex = (shardIndex + 1) % numShards;
			}

			for (FileWriter writer : writers) {
				writer.close();
			}

			System.out.println("File sharding completed successfully.");
		} catch (IOException e) {
			System.out.println("Error during file sharding: " + e.getMessage());
		}
	}

	public static void main(String[] args) {
		String inputFile = FileShardingByNoOfShards.class.getClassLoader().getResource("demo.txt").getPath();
		shardFiles(inputFile, 4, "shard");
	}

}
