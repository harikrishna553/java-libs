package com.sample.app.entity;

import java.sql.Types;

import org.hibernate.Length;
import org.hibernate.annotations.JdbcTypeCode;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name = "string_type_demo")
public class StringTypeDemo {

	@Id
	private Integer id;

	// will be mapped using VARCHAR
	private String str1;

	@Lob
	private String clobStr;

	@Column(length = 10)
	private String strLength10;

	@Column(length = Length.LONG)
	private String strLengthMax1;

	@JdbcTypeCode(Types.LONGVARCHAR)
	private String strLengthMax2;

	@Column(length = Length.LONG32)
	private String strLengthMax3;

	public StringTypeDemo(Integer id, String str1, String clobStr, String strLength10, String strLengthMax1,
			String strLengthMax2, String strLengthMax3) {
		this.id = id;
		this.str1 = str1;
		this.clobStr = clobStr;
		this.strLength10 = strLength10;
		this.strLengthMax1 = strLengthMax1;
		this.strLengthMax2 = strLengthMax2;
		this.strLengthMax3 = strLengthMax3;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getStr1() {
		return str1;
	}

	public void setStr1(String str1) {
		this.str1 = str1;
	}

	public String getClobStr() {
		return clobStr;
	}

	public void setClobStr(String clobStr) {
		this.clobStr = clobStr;
	}

	public String getStrLength10() {
		return strLength10;
	}

	public void setStrLength10(String strLength10) {
		this.strLength10 = strLength10;
	}

	public String getStrLengthMax1() {
		return strLengthMax1;
	}

	public void setStrLengthMax1(String strLengthMax1) {
		this.strLengthMax1 = strLengthMax1;
	}

	public String getStrLengthMax2() {
		return strLengthMax2;
	}

	public void setStrLengthMax2(String strLengthMax2) {
		this.strLengthMax2 = strLengthMax2;
	}

	public String getStrLengthMax3() {
		return strLengthMax3;
	}

	public void setStrLengthMax3(String strLengthMax3) {
		this.strLengthMax3 = strLengthMax3;
	}

	@Override
	public String toString() {
		return "StringTypeDemo [id=" + id + ", str1=" + str1 + ", clobStr=" + clobStr + ", strLength10=" + strLength10
				+ ", strLengthMax1=" + strLengthMax1 + ", strLengthMax2=" + strLengthMax2 + ", strLengthMax3="
				+ strLengthMax3 + "]";
	}

}