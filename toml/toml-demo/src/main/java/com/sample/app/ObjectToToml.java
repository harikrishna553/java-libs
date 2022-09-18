package com.sample.app;

import java.io.IOException;

import com.fasterxml.jackson.dataformat.toml.TomlMapper;
import com.sample.app.dto.Employee;

public class ObjectToToml {

	public static void main(String[] args) throws IOException {
		TomlMapper mapper = new TomlMapper();
		
		Employee emp = new Employee();
		emp.setId(1);
		emp.setFirstName("Rama Devi");
		emp.setLastName("G");
		
		String toml = mapper.writeValueAsString(emp);
		System.out.println(toml);
		
	}

}
