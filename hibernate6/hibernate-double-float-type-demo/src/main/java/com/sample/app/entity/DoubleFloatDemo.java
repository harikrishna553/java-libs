package com.sample.app.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "double_float_demo")
public class DoubleFloatDemo {

	@Id
	private Integer id;

	private Double d;

	private Float f;

	public DoubleFloatDemo(Integer id, Double d, Float f) {
		this.id = id;
		this.d = d;
		this.f = f;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getD() {
		return d;
	}

	public void setD(Double d) {
		this.d = d;
	}

	public Float getF() {
		return f;
	}

	public void setF(Float f) {
		this.f = f;
	}

}