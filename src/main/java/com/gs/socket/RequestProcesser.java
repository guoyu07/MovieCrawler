package com.gs.socket;

import com.gs.DAO.UserDAO;
import com.gs.model.User;

public class RequestProcesser {
	public static String getQueryString(Request req) throws Exception {
		if (validate(req))
			return req.getQueryString();
		else
			throw new Exception("身份验证未通过");
	}

	private static boolean validate(Request req) {
		UserDAO userDAO = new UserDAO();
		if(!userDAO.checkUserWithCpuid(req.getCpuid())) return false;
		User user = userDAO.getUser(req.getCpuid());
		if (user.getUsername().equals(req.getUsername()))
			return true;
		else {
			return false;
		}
	}
}
