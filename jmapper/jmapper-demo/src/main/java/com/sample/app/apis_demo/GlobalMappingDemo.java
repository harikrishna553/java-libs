package com.sample.app.apis_demo;

import static com.googlecode.jmapper.api.JMapperAPI.global;
import static com.googlecode.jmapper.api.JMapperAPI.mappedClass;

import com.googlecode.jmapper.JMapper;
import com.googlecode.jmapper.api.JMapperAPI;

public class GlobalMappingDemo {

	public static void main(String[] args) {
		final Employee employee = new Employee();
		employee.setId(123);
		employee.setName("Krishna");
		employee.setAge(28);

		final JMapperAPI jmapperApi = new JMapperAPI()
				.add(mappedClass(EmployeeDto1.class)
						.add(global().excludedAttributes("empAge")));

		final JMapper<EmployeeDto1, Employee> employeeMapper = new JMapper<>(EmployeeDto1.class, Employee.class, jmapperApi);

		final EmployeeDto1 employeeDto = employeeMapper.getDestination(employee);

		System.out.println(employee);
		System.out.println(employeeDto);

	}

}