package com.gs.crawler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.log4j.Logger;

import com.gs.DAO.MovieDAO;
import com.gs.crawler.extractor.Extractor;
import com.gs.model.Movie;

public class Crawler implements Runnable {
	private Logger logger = Logger.getLogger(this.getClass());
	private final String server;
	private final File input;

	public void run() {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(input));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		String page = "";
		MovieDAO dao = new MovieDAO();
		try {
			while ((page = br.readLine()) != null) {
				Movie movie = null;
				String html = "";
				try {
					html = WebPageDownloader.down(server, page);
				} catch (IOException e) {
					e.printStackTrace();
				}
				movie = Extractor.extract(html);
				movie.setUrl(server + page);
				dao.save(movie);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				dao.close();
			} catch (IOException e) {
				logger.error("MovieDAO关闭错误"+e.getMessage());
				e.printStackTrace();
			}
			try {
				br.close();
			} catch (IOException e) {
				logger.error("BufferedReader关闭错误"+e.getMessage());
				e.printStackTrace();
			}
		}
	}

	public Crawler(String server, File input) {
		this.server = server;
		this.input = input;
	}

}
