package com.sample.app.xml_config_demo;

import com.googlecode.jmapper.JMapper;

public class GlobalMappingDemo {
	public static void main(String[] args) {
		final Employee employee = new Employee();
		employee.setId(123);
		employee.setName("Krishna");
		employee.setAge(28);

		final JMapper<EmployeeDto1, Employee> employeeMapper = new JMapper<>(EmployeeDto1.class, Employee.class,
				"global_employee_mapper.xml");

		final EmployeeDto1 employeeDto = employeeMapper.getDestination(employee);

		System.out.println(employee);
		System.out.println(employeeDto);
	}
}

