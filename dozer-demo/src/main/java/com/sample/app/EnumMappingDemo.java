package com.sample.app;

import java.io.IOException;
import java.util.Arrays;

import org.dozer.DozerBeanMapper;

import com.sample.app.model.ProcessState;


public class EnumMappingDemo {
	public static void main(String[] args) throws IOException {

		DozerBeanMapper mapper = new DozerBeanMapper();
		mapper.setMappingFiles(Arrays.asList("enum_mapping.xml"));

		com.sample.app.model.Process process1 = new com.sample.app.model.Process("process1", ProcessState.STARTED);
		com.sample.app.model.Thread thread1 = mapper.map(process1, com.sample.app.model.Thread.class);
		com.sample.app.model.Process process2 = mapper.map(thread1, com.sample.app.model.Process.class);

		System.out.println("process1 : " + process1);
		System.out.println("thread1 : " + thread1);
		System.out.println("process2 : " + process2);

	}

}
