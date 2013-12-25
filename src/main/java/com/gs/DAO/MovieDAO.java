package com.gs.DAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.gs.model.Movie;

public class MovieDAO {
	private Logger logger = Logger.getLogger(this.getClass());
	private Connection connection;
	public MovieDAO(){
		DbcpBean dbcp = DbcpBean.getInstance();
		connection = dbcp.getConn();
	}
	public void save(Movie m) {
		Statement stmt = null;
		try {
			String sql = "INSERT INTO `movie`.`movie` (`name`, `url`, `category`, `qvod`) VALUES ('"+m.getName()+"', '"+m.getUrl()+"', '"+m.getCategory()+"', '"+m.getQvod()+"');";
			stmt = (Statement) connection.createStatement(); // 创建用于执行静态sql语句的Statement对象
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}
	

}
