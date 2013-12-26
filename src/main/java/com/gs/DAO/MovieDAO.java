package com.gs.DAO;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.gs.model.Movie;

public class MovieDAO implements Closeable{
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
	
	public boolean checkMovieWithID(int id){
		Statement stmt = null;
		ResultSet rs = null;
		try {
			String sql = "select count(*) from movie where id = "+id+";";
			stmt = (Statement) connection.createStatement(); // 创建用于执行静态sql语句的Statement对象
			rs = stmt.executeQuery(sql);
			if(rs.next()){return rs.getInt("count(*)") == 0 ? false :true;}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}
	public void close() throws IOException {
		try {
			connection.close();
		} catch (SQLException e) {
			logger.error("Connection 关闭错误"+e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	public Iterator<Movie> getIterator(){
		Iterator<Movie> iterator = new Iterator<Movie>() {
			private int i = 36;
			public boolean hasNext() {
				return checkMovieWithID(i);
			}

			public Movie next() {
				getMovie(i++);
				return null;
			}


			public void remove() {}
		};
		return iterator;
		
	}
	public Movie getMovie(int id) {
		Statement stmt = null;
		ResultSet rs = null;
		Movie movie = null;
		try {
			String sql = "select * from movie where id = "+id+";";
			stmt = (Statement) connection.createStatement(); // 创建用于执行静态sql语句的Statement对象
			rs = stmt.executeQuery(sql);
			if(rs.next()){
				movie  = new Movie(rs.getString("url"),rs.getString("name"),rs.getString("qvod"),rs.getString("category"));
				movie.setId(id);
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return movie;
	}
	

}
