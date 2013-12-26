package com.gs.socket;


public class Response {
	private String json;
	private int statusCode;
	private String jsonClassName;
	
	public Response() {
	}
	
	public Response(String json, int statusCode) {
		this.json = json;
		this.statusCode = statusCode;
	}

	public String getJson() {
		return json;
	}

	protected void setJson(String json) {
		this.json = json;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getJsonClassName() {
		return jsonClassName;
	}

	public void setJsonClassName(String jsonClassName) {
		this.jsonClassName = jsonClassName;
	}
}
