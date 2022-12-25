package com.sample.app.relational.mapping.apis;

import static com.googlecode.jmapper.api.JMapperAPI.attribute;
import static com.googlecode.jmapper.api.JMapperAPI.mappedClass;

import com.googlecode.jmapper.RelationalJMapper;
import com.googlecode.jmapper.api.JMapperAPI;

public class RelationalMapping {

	public static void main(String[] args) {
		final JMapperAPI jmapperApi = new JMapperAPI().add(mappedClass(Employee.class)
				.add(attribute("id").value("id").targetClasses(EmployeeDto1.class, EmployeeDto2.class))
				.add(attribute("name").targetAttributes("empName", "myName").targetClasses(EmployeeDto1.class,
						EmployeeDto2.class))
				.add(attribute("age").targetAttributes("empAge", "myAge").targetClasses(EmployeeDto1.class,
						EmployeeDto2.class)));

		final RelationalJMapper<Employee> relationalMapper = new RelationalJMapper<>(Employee.class, jmapperApi);
		final Employee emp = new Employee();
		emp.setId(1);
		emp.setAge(34);
		emp.setName("Krishna");

		final EmployeeDto1 employeeDto1 = relationalMapper.oneToMany(EmployeeDto1.class, emp);
		final EmployeeDto2 employeeDto2 = relationalMapper.oneToMany(EmployeeDto2.class, emp);

		System.out.println(emp);
		System.out.println(employeeDto1);
		System.out.println(employeeDto2);
	}

}