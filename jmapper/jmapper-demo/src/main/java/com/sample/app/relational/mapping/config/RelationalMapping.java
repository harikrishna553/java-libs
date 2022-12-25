package com.sample.app.relational.mapping.config;

import com.googlecode.jmapper.RelationalJMapper;

public class RelationalMapping {

	public static void main(String[] args) {
		final Employee emp = new Employee();
		emp.setId(1);
		emp.setAge(34);
		emp.setName("Krishna");

		final RelationalJMapper<Employee> relationalMapper = new RelationalJMapper<>(Employee.class,
				"relational_mapping_one_to_many.xml");

		final EmployeeDto1 employeeDto1 = relationalMapper.oneToMany(EmployeeDto1.class, emp);
		final EmployeeDto2 employeeDto2 = relationalMapper.oneToMany(EmployeeDto2.class, emp);

		System.out.println(emp);
		System.out.println(employeeDto1);
		System.out.println(employeeDto2);
	}

}
