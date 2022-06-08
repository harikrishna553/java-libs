package com.sample.app.streams;

import java.net.URL;

import org.apache.flink.api.common.io.FilePathFilter;
import org.apache.flink.api.java.io.TextInputFormat;
import org.apache.flink.core.fs.Path;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.FileProcessingMode;

import com.sample.app.csvreader.CsvReaderHelloWorld;

public class DataStreamFileProcessingMode {
	
	public static String resourceFilePath(String resourceFile) {
		ClassLoader classLoader = CsvReaderHelloWorld.class.getClassLoader();
		URL url = classLoader.getResource(resourceFile);
		return url.getPath();
	}
	
	
	public static void main(String[] args) throws Exception {
		StreamExecutionEnvironment executionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment();
		executionEnvironment.setParallelism(1);

		TextInputFormat textInputFormat = new TextInputFormat(new Path(resourceFilePath("primes.txt")));
        textInputFormat.setFilesFilter(FilePathFilter.createDefaultFilter());
        
		executionEnvironment
		.readFile(textInputFormat, "/Users/Shared/demo", FileProcessingMode.PROCESS_CONTINUOUSLY, 2000l)
		.executeAndCollect()
		.forEachRemaining(System.out::println);
	}

}


