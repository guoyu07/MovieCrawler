package com.gs.model;

public class Movie {
	private String url;
	private String name;
	private String qvod;
	private String category;
	public Movie(String url, String name, String qvod, String category) {
		this.url = url;
		this.name = name;
		this.qvod = qvod;
		this.category = category;
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
}
