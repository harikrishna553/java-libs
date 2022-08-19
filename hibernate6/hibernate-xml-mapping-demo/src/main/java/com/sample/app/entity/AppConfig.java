package com.sample.app.entity;

import java.util.Map;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "app_config")
public class AppConfig {
	@Id
	private Integer id;

	@JdbcTypeCode(SqlTypes.SQLXML)
	private Map<String, Object> config;

	public AppConfig(Integer id, Map<String, Object> config) {
		this.id = id;
		this.config = config;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Map<String, Object> getConfig() {
		return config;
	}

	public void setConfig(Map<String, Object> config) {
		this.config = config;
	}

	@Override
	public String toString() {
		return "AppConfig [id=" + id + ", config=" + config + "]";
	}

}
