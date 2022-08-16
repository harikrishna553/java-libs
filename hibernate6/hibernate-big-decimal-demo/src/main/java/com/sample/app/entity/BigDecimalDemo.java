package com.sample.app.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "big_decimal_demo")
public class BigDecimalDemo {

	@Id
	private Integer id;

	private BigDecimal bigDecimal1;

	@Column(precision = 100, scale = 50)
	private BigDecimal bigDecimal2;

	public BigDecimalDemo(Integer id, BigDecimal bigDecimal1, BigDecimal bigDecimal2) {
		this.id = id;
		this.bigDecimal1 = bigDecimal1;
		this.bigDecimal2 = bigDecimal2;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BigDecimal getBigDecimal1() {
		return bigDecimal1;
	}

	public void setBigDecimal1(BigDecimal bigDecimal1) {
		this.bigDecimal1 = bigDecimal1;
	}

	public BigDecimal getBigDecimal2() {
		return bigDecimal2;
	}

	public void setBigDecimal2(BigDecimal bigDecimal2) {
		this.bigDecimal2 = bigDecimal2;
	}

}