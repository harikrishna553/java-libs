package com.sample.app.custom_mapping;

import java.time.LocalDate;

import com.googlecode.jmapper.JMapper;

public class CustomMappingDemo {

	public static void main(String[] args) {
		final Employee employee = new Employee();
		employee.setId(123);
		employee.setName("Krishna");
		employee.setDateOfBirth(LocalDate.of(1988, 6, 6));

		final JMapper<EmployeeDto, Employee> employeeMapper = new JMapper<>(EmployeeDto.class, Employee.class);

		final EmployeeDto employeeDto = employeeMapper.getDestination(employee);

		System.out.println(employee);
		System.out.println(employeeDto);

	}

}
