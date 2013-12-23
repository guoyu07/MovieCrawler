package com.gs.socket;

public class Request {
	private String cpuid;
	private String username;
	private String queryString;
	
	public Request(String cpuid, String username, String queryString) {
		this.cpuid = cpuid;
		this.username = username;
		this.queryString = queryString;
	}
	
	public String getCpuid() {
		return cpuid;
	}
	public void setCpuid(String cpuid) {
		this.cpuid = cpuid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getQueryString() {
		return queryString;
	}
	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}
	
}
