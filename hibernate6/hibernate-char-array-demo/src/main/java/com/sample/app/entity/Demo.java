package com.sample.app.entity;

import org.hibernate.annotations.Nationalized;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name = "demo-table")
public class Demo {
	@Id
	private int id;

	private char[] arr1;

	@Nationalized
	private char[] arr2;

	@Lob
	private char[] arr3;

	@Lob
	@Nationalized
	private char[] arr4;

	public Demo(int id, char[] arr1, char[] arr2, char[] arr3, char[] arr4) {
		this.id = id;
		this.arr1 = arr1;
		this.arr2 = arr2;
		this.arr3 = arr3;
		this.arr4 = arr4;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public char[] getArr1() {
		return arr1;
	}

	public void setArr1(char[] arr1) {
		this.arr1 = arr1;
	}

	public char[] getArr2() {
		return arr2;
	}

	public void setArr2(char[] arr2) {
		this.arr2 = arr2;
	}

	public char[] getArr3() {
		return arr3;
	}

	public void setArr3(char[] arr3) {
		this.arr3 = arr3;
	}

	public char[] getArr4() {
		return arr4;
	}

	public void setArr4(char[] arr4) {
		this.arr4 = arr4;
	}

}
