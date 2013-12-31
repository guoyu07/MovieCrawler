package com.gs.socket.request;

public class SearchRequest implements Request{
	private String queryString;
	private int max;
	public String getQueryString() {
		return queryString;
	}
	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}
	public int getMax() {
		return max;
	}
	public void setMax(int max) {
		this.max = max;
	}
	public SearchRequest(String queryString, int max) {
		super();
		this.queryString = queryString;
		this.max = max;
	}
	@Override
	public String toString() {
		return "SearchRequest ["
				+ (queryString != null ? "queryString=" + queryString + ", "
						: "") + "max=" + max + "]";
	}
	

	

}
