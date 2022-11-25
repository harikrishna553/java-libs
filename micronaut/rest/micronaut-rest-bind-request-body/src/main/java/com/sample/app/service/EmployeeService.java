package com.sample.app.service;

import java.util.ArrayList;
import java.util.List;

import com.sample.app.model.Employee;
import com.sample.app.model.EmployeeRequestDto;

import jakarta.inject.Singleton;

@Singleton
public class EmployeeService {
	private static final List<Employee> emps = new ArrayList<>();
	private static final Employee EMP_NOT_FOUND = new Employee();

	static {
		final Employee emp1 = new Employee("Sunil", 23);
		final Employee emp2 = new Employee("Shetty", 31);
		final Employee emp3 = new Employee("Ram", 43);
		final Employee emp4 = new Employee("Akansha", 21);

		emps.add(emp1);
		emps.add(emp2);
		emps.add(emp3);
		emps.add(emp4);

	}

	public List<Employee> all() {
		return emps;
	}

	public Employee byId(final Integer id) {
		for (Employee emp : all()) {
			if (id.equals(emp.getId())) {
				return emp;
			}
		}
		return EMP_NOT_FOUND;
	}

	public Employee lockEmployee(final Integer id) {
		Employee emp = byId(id);
		emp.setLock(true);
		return emp;
	}

	public Employee unlockEmployee(final Integer id) {
		Employee emp = byId(id);
		emp.setLock(false);
		return emp;
	}

	public Employee saveEmployee(final EmployeeRequestDto employee) {
		final Employee emp = new Employee(employee.getName(), employee.getAge());
		emps.add(emp);
		return emp;
	}
}
