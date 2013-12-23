package com.gs.socket;

import java.util.Set;

import com.gs.searcher.Hit;

public class Response {
	public Response(Set<Hit> resultSet, int statusCode) {
		this.resultSet = resultSet;
		this.statusCode = statusCode;
	}
	
	public Response() {
	}

	private Set<Hit> resultSet;
	private int statusCode;
	
	public Set<Hit> getResultSet() {
		return resultSet;
	}

	public void setResultSet(Set<Hit> resultSet) {
		this.resultSet = resultSet;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
}
