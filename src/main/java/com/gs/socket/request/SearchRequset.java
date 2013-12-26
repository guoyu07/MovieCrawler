package com.gs.socket.request;

public class SearchRequset implements Request{
	private String queryString;
	
	public SearchRequset(String queryString) {
		this.queryString = queryString;
	}

	public String getQueryString() {
		return queryString;
	}

	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}

	@Override
	public String toString() {
		return "SearchRequset ["
				+ (queryString != null ? "queryString=" + queryString : "")
				+ "]";
	}

	

}
