package com.gs.socket.request;

import com.google.gson.Gson;

public class RequestDTO{
	public RequestDTO(String json, String jsonClassName, String property) {
		this.json = json;
		this.jsonClassName = jsonClassName;
		this.property = property;
	}

	private String json;
	private String jsonClassName;
	private String property;
	
	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public String getJsonClassName() {
		return jsonClassName;
	}

	public void setJsonClassName(String jsonClassName) {
		this.jsonClassName = jsonClassName;
	}

	public RequestProperty getProperty() {
		return new Gson().fromJson(property, RequestProperty.class);
	}

	public void setProperty(String property) {
		this.property = property;
	}

	@Override
	public String toString() {
		return "RequestDTO ["
				+ (json != null ? "json=" + json + ", " : "")
				+ (jsonClassName != null ? "jsonClassName=" + jsonClassName
						+ ", " : "")
				+ (property != null ? "property=" + property : "") + "]";
	}

}
