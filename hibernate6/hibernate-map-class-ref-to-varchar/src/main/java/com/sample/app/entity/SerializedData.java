package com.sample.app.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "serialized_data")
public class SerializedData {
	@Id
	private int id;

	private Class<?> clazz;

	private byte[] serializedData;

	public SerializedData(int id, Class<?> clazz, byte[] serializedData) {
		this.id = id;
		this.clazz = clazz;
		this.serializedData = serializedData;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	public byte[] getSerializedData() {
		return serializedData;
	}

	public void setSerializedData(byte[] serializedData) {
		this.serializedData = serializedData;
	}

}
