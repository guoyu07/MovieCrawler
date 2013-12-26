package com.gs.socket.request;

public class RequestProperty {
	private String cpuid;
	private String username;

	public RequestProperty(String cpuid, String username) {
		this.cpuid = cpuid;
		this.username = username;
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

	

}
