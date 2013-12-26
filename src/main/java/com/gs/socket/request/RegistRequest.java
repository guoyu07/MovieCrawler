package com.gs.socket.request;

public class RegistRequest implements Request{
	private String username;
	private String cpuid;
	
	public RegistRequest(String username, String cpuid) {
		super();
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
