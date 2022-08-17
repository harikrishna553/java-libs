package com.sample.app.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name = "demo")
public class Demo {
	@Id
	private int id;

	@Lob
	private byte[] arr1;

	public Demo(int id, byte[] arr1) {
		this.id = id;
		this.arr1 = arr1;
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

}
