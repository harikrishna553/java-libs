package com.sample.app.entity;

import org.hibernate.Length;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "demo")
public class Demo {
	@Id
	private int id;

	// mapped to 'arr1 varbinary(255)'
	private byte[] arr1;

	// mapped to 'arr2 varbinary(30)'
	@Column(length = 30)
	private byte[] arr2;

	// mapped to 'arr3 varbinary(32600)'
	@Column(length = Length.LONG)
	private byte[] arr3;

	public Demo(int id, byte[] arr1, byte[] arr2, byte[] arr3) {
		this.id = id;
		this.arr1 = arr1;
		this.arr2 = arr2;
		this.arr3 = arr3;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public byte[] getArr1() {
		return arr1;
	}

	public void setArr1(byte[] arr1) {
		this.arr1 = arr1;
	}

	public byte[] getArr2() {
		return arr2;
	}

	public void setArr2(byte[] arr2) {
		this.arr2 = arr2;
	}

	public byte[] getArr3() {
		return arr3;
	}

	public void setArr3(byte[] arr3) {
		this.arr3 = arr3;
	}

}
