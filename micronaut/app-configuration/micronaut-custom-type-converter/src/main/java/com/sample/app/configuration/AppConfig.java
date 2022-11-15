package com.sample.app.configuration;

import com.sample.app.model.Employee;

import io.micronaut.context.annotation.Property;
import jakarta.inject.Singleton;

@Singleton
public class AppConfig {

	@Property(name = "emp1")
	protected Employee emp1;

	@Property(name = "emp2")
	protected Employee emp2;

	public Employee getEmp1() {
		return emp1;
	}

	public void setEmp1(Employee emp1) {
		this.emp1 = emp1;
	}

	public Employee getEmp2() {
		return emp2;
	}

	public void setEmp2(Employee emp2) {
		this.emp2 = emp2;
	}

}
