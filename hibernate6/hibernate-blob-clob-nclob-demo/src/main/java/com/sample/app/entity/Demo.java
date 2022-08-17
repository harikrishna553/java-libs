package com.sample.app.entity;

import java.sql.Blob;
import java.sql.Clob;
import java.sql.NClob;

import org.hibernate.annotations.Nationalized;

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
	private Clob clob;

	@Lob
	@Nationalized
	private NClob nclob;

	@Lob
	private Blob blob;

	public Demo(int id, Clob clob, NClob nclob, Blob blob) {
		this.id = id;
		this.clob = clob;
		this.nclob = nclob;
		this.blob = blob;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Clob getClob() {
		return clob;
	}

	public void setClob(Clob clob) {
		this.clob = clob;
	}

	public NClob getNclob() {
		return nclob;
	}

	public void setNclob(NClob nclob) {
		this.nclob = nclob;
	}

	public Blob getBlob() {
		return blob;
	}

	public void setBlob(Blob blob) {
		this.blob = blob;
	}

}
