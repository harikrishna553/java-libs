package com.sample.app.service;

import java.util.ArrayList;
import java.util.List;

import com.sample.app.model.Employee;

import jakarta.inject.Singleton;

@Singleton
public class EmployeeService {
	private static final List<Employee> emps = new ArrayList<>();
	private static final Employee EMP_NOT_FOUND = new Employee();

	private static int counter = 4;

	static {
		Employee emp1 = new Employee(1, "Sunil", 23);
		Employee emp2 = new Employee(2, "Shetty", 31);
		Employee emp3 = new Employee(3, "Ram", 43);
		Employee emp4 = new Employee(4, "Akansha", 21);

		emps.add(emp1);
		emps.add(emp2);
		emps.add(emp3);
		emps.add(emp4);

	}

	public List<Employee> all() {
		return emps;
	}

	public Employee add(final Employee emp) {
		emp.setId(counter);
		counter++;

		emps.add(emp);
		return emp;
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
}
