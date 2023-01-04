package com.sample.app.filters;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hubspot.jinjava.Jinjava;
import com.sample.app.util.FileUtil;

public class GroupByFilterDemo {

	public static class Employee {
		private int id;
		private String name;
		private String gender;

		public Employee(int id, String name, String gender) {
			this.id = id;
			this.name = name;
			this.gender = gender;
		}

		public int getId() {
			return id;
		}

		public String getName() {
			return name;
		}

		public String getGender() {
			return gender;
		}

	}

	public static void main(String[] args) throws IOException {
		final Employee emp1 = new Employee(1, "Ram", "male");
		final Employee emp2 = new Employee(2, "Harika", "female");
		final Employee emp3 = new Employee(3, "Raghu", "male");
		final Employee emp4 = new Employee(4, "Santhi", "female");
		final Employee emp5 = new Employee(5, "Hari", "male");

		final List<Employee> emps = Arrays.asList(emp1, emp2, emp3, emp4, emp5);

		final Map<String, Object> data = new HashMap<>();
		data.put("emps", emps);

		final Jinjava jinjava = new Jinjava();

		final String template = FileUtil.resourceAsString("filters/groupbyFilter.jinja");

		final String finalDocument = jinjava.render(template, data);
		System.out.println(finalDocument);
	}
}
