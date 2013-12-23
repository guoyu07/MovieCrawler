package com.gs.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.gs.model.User;

public class UserDAO {
	private Logger logger = Logger.getLogger(this.getClass());
	private Connection connection;
	public UserDAO(){
		DbcpBean dbcp = DbcpBean.getInstance();
		connection = dbcp.getConn();
	}
	public User getUser(String cpuid) {
		Statement stmt = null;
		ResultSet rs = null;
		User user = null;
		try {
			stmt = connection.createStatement();
			String sql = "select * from user where cpuid = \"" + cpuid+"\"";
			logger.debug(sql);
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				user  = new User(rs.getString("username"),rs.getString("cpuid"));
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return user;
	}

}
