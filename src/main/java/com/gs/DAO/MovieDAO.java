package com.gs.DAO;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import java.util.Set;

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
	
	public boolean checkMovieWithURL(String url){
		Statement stmt = null;
		ResultSet rs = null;
		try {
			String sql = "select count(*) from movie where url = \""+url+"\";";
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
	
	public Movie getMovie(String url) {
		Statement stmt = null;
		ResultSet rs = null;
		Movie movie = null;
		try {
			String sql = "select * from movie where url = \""+url+"\";";
			stmt = (Statement) connection.createStatement(); // 创建用于执行静态sql语句的Statement对象
			rs = stmt.executeQuery(sql);
			if(rs.next()){
				movie  = new Movie(rs.getString("url"),rs.getString("name"),rs.getString("qvod"),rs.getString("category"));
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return movie;
	}
	
	public Set<Movie> getMovies(){
		Set<Movie> set = new HashSet<Movie>();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			String sql = "select * from movie;";
			stmt = (Statement) connection.createStatement(); // 创建用于执行静态sql语句的Statement对象
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				Movie movie  = new Movie(rs.getString("url"),rs.getString("name"),rs.getString("qvod"),rs.getString("category"));
				set.add(movie);
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return set;
	}
	
	public Set<Movie> getRadomMovies() throws IOException{
		Set<Movie> set = getMovies();
		Set<Movie> resuleset = new HashSet<Movie>();
		LinkedList<Movie> list = new LinkedList<Movie>(set);
		for (int i = 0; i < 18; i++) {
			Movie m  = list.remove(new Random().nextInt(set.size()));
			resuleset.add(m);
		}
		return resuleset;
		
	}
	

}
