package com.sample.app;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.dataformat.toml.TomlMapper;
import com.sample.app.dto.Employee;

public class TomlToObject {

	public static void main(String[] args) throws JsonMappingException, JsonProcessingException {
		String tomlStr = "id = 1\n"
				+ "firstName = 'Rama Devi'\n"
				+ "lastName = 'G'\n";
		
		TomlMapper tomlMapper = new TomlMapper();
		
		Employee deserializedEmp = tomlMapper.readValue(tomlStr, Employee.class);
		
		System.out.println(deserializedEmp);
		
	}
}
