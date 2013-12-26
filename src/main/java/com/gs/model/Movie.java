package com.gs.model;

public class Movie {
	private String url;
	private String name;
	private String qvod;
	private String category;
	private int id;
	public Movie(String url, String name, String qvod, String category) {
		this.url = url;
		this.name = name;
		this.qvod = qvod;
		this.category = category;
	}
	public Movie() {
		// TODO Auto-generated constructor stub
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getQvod() {
		return qvod;
	}
	public void setQvod(String qvod) {
		this.qvod = qvod;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "Movie [" + (url != null ? "url=" + url + ", " : "")
				+ (name != null ? "name=" + name + ", " : "")
				+ (qvod != null ? "qvod=" + qvod + ", " : "")
				+ (category != null ? "category=" + category + ", " : "")
				+ "id=" + id + "]";
	}
}
