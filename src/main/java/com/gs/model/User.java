package com.gs.model;

public class User {
	private String username;
	private String cpuid;
	public User(String username, String cpuid) {
		this.username = username;
		this.cpuid = cpuid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getCpuid() {
		return cpuid;
	}
	public void setCpuid(String cpuid) {
		this.cpuid = cpuid;
	}
}
