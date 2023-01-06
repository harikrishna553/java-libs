package com.sample.app.filters;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hubspot.jinjava.Jinjava;
import com.sample.app.util.FileUtil;

public class MapFilterOnSpecificAttribute {

	public static class Employee {
		private int id;
		private String name;
		private int age;

		public Employee(int id, String name, int age) {
			this.id = id;
			this.name = name;
			this.age = age;
		}

		public int getId() {
			return id;
		}

		public String getName() {
			return name;
		}

		public int getAge() {
			return age;
		}

	}

	public static void main(String[] args) throws IOException {
		final Jinjava jinjava = new Jinjava();
		
		final Employee emp1 = new Employee(1, "Krishna", 34);
		final Employee emp2 = new Employee(2, "Ram", 35);
		final Employee emp3 = new Employee(3, "Balu", 43);
		final Employee emp4 = new Employee(4, "Gopi", 36);
		
		final List<Employee> emps = Arrays.asList(emp1, emp2, emp3, emp4);
		
		final Map<String, Object> data = new HashMap<>();
		data.put("emps", emps);
		
		final String template = FileUtil.resourceAsString("filters/mapFilterOnAttribute.jinja");

		final String finalDocument = jinjava.render(template, data);
		System.out.println(finalDocument);
	}
}
