package com.sample.app.dto;

public class Post {

	private Integer id;
	private String name;
	private String tagIds;
	private Integer authorId;

	public Post() {
	}

	public Post(Integer id, String name, String tagIds, Integer authorId) {
		this.id = id;
		this.name = name;
		this.tagIds = tagIds;
		this.authorId = authorId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTagIds() {
		return tagIds;
	}

	public void setTagIds(String tagIds) {
		this.tagIds = tagIds;
	}

	public Integer getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}

}
