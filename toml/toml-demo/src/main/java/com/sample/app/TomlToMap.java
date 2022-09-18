package com.sample.app;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.toml.TomlMapper;
import com.sample.app.dto.Employee;

public class TomlToMap {

	public static void main(String[] args) throws JsonProcessingException {
		Employee emp1 = new Employee();
		emp1.setFirstName("Krishna");
		emp1.setId(1);
		emp1.setLastName("Gurram");

		Employee emp2 = new Employee();
		emp2.setFirstName("Ram");
		emp2.setId(2);
		emp2.setLastName("Maj");

		Map<Integer, Employee> empsMap = new HashMap<>();
		empsMap.put(1, emp1);
		empsMap.put(2, emp2);

		TomlMapper tomlMapper = new TomlMapper();
		String toml = tomlMapper.writeValueAsString(empsMap);
		System.out.println(toml);

		Map<Integer, Employee> map = tomlMapper.readValue(
				toml, new TypeReference<Map<Integer, Employee>>() {
		});

		for (Integer key : map.keySet()) {
			Employee emp = map.get(key);
			System.out.println(emp);
		}

	}

}