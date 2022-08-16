package com.sample.app.entity;

import java.math.BigInteger;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "big_integer_demo")
public class BigIntegerDemo {

	@Id
	private Integer id;

	private BigInteger bigIntVal;

	public BigIntegerDemo(Integer id, BigInteger bigIntVal) {
		this.id = id;
		this.bigIntVal = bigIntVal;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BigInteger getBigIntVal() {
		return bigIntVal;
	}

	public void setBigIntVal(BigInteger bigIntVal) {
		this.bigIntVal = bigIntVal;
	}

}