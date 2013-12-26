package com.gs.socket.request;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.gs.DAO.UserDAO;
import com.gs.model.User;

public class RequestDTOProcesser {
	private static final UserDAO userDAO = new UserDAO();
	public static final RequestDTO pack(Request req,RequestProperty property) {
		String json = new Gson().toJson(req);
		String jsonClassName = req.getClass().getName();
		String propertyJson = new Gson().toJson(property);
		return new RequestDTO(json,jsonClassName,propertyJson);
	}
	
	public static final Request unpack(RequestDTO dto) throws JsonSyntaxException, ClassNotFoundException,Exception{
		if(validate(dto))
		return (Request) new Gson().fromJson(dto.getJson(), Class.forName(dto.getJsonClassName()));
		else {throw new Exception("身份验证未通过");}
	}
	
	public static final boolean validate(RequestDTO dto) {
		if(!userDAO.checkUserWithCpuid(dto.getProperty().getCpuid())) return false;
		User user = userDAO.getUser(dto.getProperty().getCpuid());
		if (user.getUsername().equals(dto.getProperty().getUsername()))
			return true;
		else {
			return false;
		}
	}
}
