package com.example.administrator.store3.entity;

public class SearchAutoData {

	private String id ="";
	private String content = "";
	public SearchAutoData setContent(String content) {
		this.content = content;
		return this;
	}

	public String getId() {
		return id;
	}
	public SearchAutoData setId(String id) {
		this.id = id;
		return this;
	}
	public String getContent() {
		return content;
	}


	@Override
	public String toString() {
		return "SearchAutoData{" +
				"id='" + id + '\'' +
				", content='" + content + '\'' +
				'}';
	}
}
