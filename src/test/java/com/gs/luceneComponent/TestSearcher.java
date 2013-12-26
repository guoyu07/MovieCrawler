package com.gs.luceneComponent;

import java.io.IOException;

import org.junit.Test;

import com.gs.DAO.MovieDAO;
import com.gs.model.Movie;

public class TestSearcher {

	
	@Test
	public void test() throws IOException{
		Searcher s = new Searcher();
		MovieDAO dao = new MovieDAO();
		for(Movie m : s.search("Test", 99)){
			System.out.println(dao.getMovie(m.getId()));
		}
		dao.close();
	}
}
