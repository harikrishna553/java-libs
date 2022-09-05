package com.sample.app.entity;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "author")
public class Author {

	@Id
	private Integer authorId;

	private String name;

	public Author(Integer authorId, String name) {
		this.authorId = authorId;
		this.name = name;
	}

	public Integer getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Author [empId=" + authorId + ", name=" + name + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(authorId, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Author other = (Author) obj;
		return Objects.equals(authorId, other.authorId) && Objects.equals(name, other.name);
	}

}