package com.gs.socket.response;

import com.gs.socket.request.SearchRequest;

public class ResponseProcesser {
	public static final String searchRequestProcess(SearchRequest req){
		return req.getQueryString();
	}
	
}
