package com.sample.app.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "integer_demo")
public class IntegerDemo {

	@Id
	private Integer id;

	Byte byteVal;

	Short shortVal;

	Integer intVal;

	Long longVal;

	public IntegerDemo(Integer id, Byte byteVal, Short shortVal, Integer intVal, Long longVal) {
		this.id = id;
		this.byteVal = byteVal;
		this.shortVal = shortVal;
		this.intVal = intVal;
		this.longVal = longVal;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Byte getByteVal() {
		return byteVal;
	}

	public void setByteVal(Byte byteVal) {
		this.byteVal = byteVal;
	}

	public Short getShortVal() {
		return shortVal;
	}

	public void setShortVal(Short shortVal) {
		this.shortVal = shortVal;
	}

	public Integer getIntVal() {
		return intVal;
	}

	public void setIntVal(Integer intVal) {
		this.intVal = intVal;
	}

	public Long getLongVal() {
		return longVal;
	}

	public void setLongVal(Long longVal) {
		this.longVal = longVal;
	}

}