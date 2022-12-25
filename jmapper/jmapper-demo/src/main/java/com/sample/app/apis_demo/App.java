package com.sample.app.apis_demo;

import static com.googlecode.jmapper.api.JMapperAPI.attribute;
import static com.googlecode.jmapper.api.JMapperAPI.mappedClass;

import com.googlecode.jmapper.JMapper;
import com.googlecode.jmapper.api.JMapperAPI;

public class App {

	public static void main(String[] args) {
		final Employee employee = new Employee();
		employee.setId(123);
		employee.setName("Krishna");
		employee.setAge(28);

		final JMapperAPI jmapperApi = new JMapperAPI()
				.add(mappedClass(EmployeeDto.class)
				.add(attribute("empId").value("id"))
				.add(attribute("empName").value("name"))
				.add(attribute("empAge").value("age")));

		final JMapper<EmployeeDto, Employee> employeeMapper = new JMapper<>(EmployeeDto.class, Employee.class, jmapperApi);
		
		EmployeeDto employeeDto = employeeMapper.getDestination(employee);

		System.out.println(employee);
		System.out.println(employeeDto);

	}
}
