package com.sample.app.entity;

import java.net.InetAddress;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "domain")
public class Domain {
	@Id
	private Integer id;

	private InetAddress inetAddress;

	public Domain(Integer id, InetAddress inetAddress) {
		this.id = id;
		this.inetAddress = inetAddress;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public InetAddress getInetAddress() {
		return inetAddress;
	}

	public void setInetAddress(InetAddress inetAddress) {
		this.inetAddress = inetAddress;
	}

	@Override
	public String toString() {
		return "Domain [id=" + id + ", inetAddress=" + inetAddress + "]";
	}

}
