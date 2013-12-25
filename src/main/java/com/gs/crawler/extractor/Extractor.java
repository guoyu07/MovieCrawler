package com.gs.crawler.extractor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.gs.model.Movie;

public class Extractor {
	public static final Movie extract(String html){
		return new Movie("", titleProcess(html), qvodProcess(html), categoryProcess(html));
	}
	
	private final static String qvodProcess(String html) {
		String qvod = "";
		String regex = "qvod://(.*?)</a>";
		Pattern pt = Pattern.compile(regex);
		Matcher mt = pt.matcher(html);
		while (mt.find()) {
			qvod = mt.group(1);
		}
		return "qvod://" + qvod;
	}
	
	private final static String titleProcess(String html) {
		String title = "";
		String regex = "<h1>(.*?)</h1>";
		Pattern pt = Pattern.compile(regex);
		Matcher mt = pt.matcher(html);
		while (mt.find()) {
			title = mt.group(1);
		}
		return title;
	}
	
	private final static String categoryProcess(String html) {
		String category = "";
		String regex = "<p>所属分类：(.*?)</p>";
		Pattern pt = Pattern.compile(regex);
		Matcher mt = pt.matcher(html);
		while (mt.find()) {
			category = mt.group(1);
		}
		return category;
	}
}
