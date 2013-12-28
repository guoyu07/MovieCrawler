package com.gs.socket.request;

public class RegistRequest implements Request{
	private String username;
	
	public RegistRequest(String username) {
		super();
		this.username = username;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
}
