package com.sample.app;

import java.io.IOException;

import com.sample.app.dto.Employee;
import com.sample.loghash.storage.*;
import com.sample.loghash.util.JsonUtil;

import java.util.*;

public class App {

	private static List<Employee> getNEmployees(int noOfEmployees, int startId) {
		List<Employee> emps = new ArrayList<>();
		for (int i = startId; i < (startId + noOfEmployees); i++) {
			emps.add(new Employee("" + i, "name_" + i, "city_" + i));
		}
		return emps;
	}

	private static Map<String, String> convertToKeyValuePairs(List<Employee> emps) {
		Map<String, String> resultMap = new HashMap<>();

		for (Employee emp : emps) {
			String key = emp.getId();
			String value = JsonUtil.getJson(emp);
			resultMap.put(key, value);
		}
		return resultMap;
	}

	public static void main(String[] args) throws IOException {
		String contentFilePath = "/Users/Shared/storage-engine/content.dat";
		String offsetFilePath = "/Users/Shared/storage-engine/offsets.json";

		try (StorageEngine engine = StorageEngine.of(contentFilePath, offsetFilePath, "DELETED")) {

			List<Employee> emps = getNEmployees(5, 1);
			Map<String, String> entries = convertToKeyValuePairs(emps);
			engine.writeKeys(entries);

			System.out.println("All the entries in storage engine are : ");
			System.out.println(engine.readKeys());

			System.out.println("\nUpdate entry with id '3'");
			entries = convertToKeyValuePairs(Arrays.asList(new Employee("3", "updated_name", "updated_city")));
			engine.writeKeys(entries);

			System.out.println("\nAll the entries in storage engine are : ");
			System.out.println(engine.readKeys());

			System.out.println("\nDelete entries with keys 1 and 4");
			engine.delete("1");
			engine.delete("4");
			System.out.println("\nAll the entries in storage engine are : ");
			System.out.println(engine.readKeys());

			System.out.println("\nEntry with key 2 is ");
			System.out.println(engine.readKey("2"));
			
			// Call this when you see there are many stale entries to cleanup
			// engine.compact();
		}
	}

}
