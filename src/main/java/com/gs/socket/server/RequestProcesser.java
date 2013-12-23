package com.gs.socket.server;

import com.gs.DAO.UserDAO;
import com.gs.DAO.UserDAOPool;
import com.gs.model.User;
import com.gs.socket.Request;

public class RequestProcesser {
	public String process(Request req){
		UserDAO userDAO = UserDAOPool.getUserDAO();
		User user = userDAO.getUser(req.getCpuid());
		if(user.getUsername().equals(req.getUsername()))return req.getQueryString();
		else {return null;}
	}
}
