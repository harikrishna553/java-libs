package com.sample.app.annotations_demo;

import com.googlecode.jmapper.JMapper;

public class App {

	public static void main(String[] args) {
		final Employee employee = new Employee();
		employee.setId(123);
		employee.setName("Krishna");
		employee.setAge(28);

		final JMapper<EmployeeDto, Employee> employeeMapper = new JMapper<>(EmployeeDto.class, Employee.class);

		final EmployeeDto employeeDto = employeeMapper.getDestination(employee);

		System.out.println(employee);
		System.out.println(employeeDto);
	}

}
