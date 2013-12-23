package com.gs.socket;

import java.util.Set;

import com.gs.searcher.Hit;

public class Response {
	private Set<Hit> resultSet;

	public Set<Hit> getResultSet() {
		return resultSet;
	}

	public void setResultSet(Set<Hit> resultSet) {
		this.resultSet = resultSet;
	}
}
