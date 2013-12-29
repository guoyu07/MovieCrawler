package com.gs.crawler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.gs.DAO.MovieDAO;
import com.gs.model.Movie;

public class CategoryCrawler {
	public void crawl() throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(new File(
				"D://Test//zhifu.data")));
		String line;
		MovieDAO dao = new MovieDAO();
		int i = 0;
		while((line = br.readLine())!=null){
			String html;
			System.out.println(i++);
			try {
				html = WebPageDownloader.down("www.88nini.com", "/view/" + line);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
			dao.save(new Movie("www.88nini.com" + "/view/" + line,
					titleProcess(html), qvodProcess(html),
					categoryProcess(html)));
		}
		dao.close();
		br.close();
	}
	
	private String qvodProcess(String html) {
		String qvod = "";
		String regex = "qvod://(.*?)</a>";
		Pattern pt = Pattern.compile(regex);
		Matcher mt = pt.matcher(html);
		while (mt.find()) {
			qvod = mt.group(1);
		}
		return "qvod://" + qvod;
	}

	private String titleProcess(String html) {
		String title = "";
		String regex = "<li class=\"title\">(.*?)</li>";
		Pattern pt = Pattern.compile(regex);
		Matcher mt = pt.matcher(html);
		while (mt.find()) {
			title = mt.group(1);
		}
		return title;
	}

	private String categoryProcess(String html) {
		String category = "";
		String regex = "<strong>(.*?)</strong>";
		Pattern pt = Pattern.compile(regex);
		Matcher mt = pt.matcher(html);
		while (mt.find()) {
			category = mt.group(1);
		}
		return category;
	}
}
