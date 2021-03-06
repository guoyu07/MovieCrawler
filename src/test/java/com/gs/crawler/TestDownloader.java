package com.gs.crawler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.gs.DAO.MovieDAO;
import com.gs.model.Movie;

public class TestDownloader {

	@Test//6sw-69 5qj-31 1yz-406
	public void test1() throws UnknownHostException, IOException {
		String strServer = "";//TODO
		FileWriter fw = null;
		// 起始页面，/为根页
		try {
			File file = new File("D://Test//.data");
			fw = new FileWriter(file, true);
			for (int i = 1; i <= 406; i++) {
				// 设置端口，通常http端口不就是80罗，你在地址栏上没输就是这个值
				int port = 80;
				// 用域名反向获得IP地址
				InetAddress addr = InetAddress.getByName(strServer);
				// 建立一个Socket
				Socket socket = new Socket(addr, port);
				BufferedWriter wr = null;
				// 发送命令,无非就是在Socket发送流的基础上加多一些握手信息，详情请了解HTTP协议
				wr = new BufferedWriter(new OutputStreamWriter(
						socket.getOutputStream(), "utf8"));
				String strPage = "/vodlist/1_" + String.valueOf(i) + ".html";
				System.out.println(strPage);
				wr.write("GET " + strPage + " HTTP/1.0\r\n");
				wr.write("HOST:" + strServer + "\r\n");
				wr.write("Accept:*/*\r\n");
				wr.write("\r\n");
				wr.flush();
				// 接收Socket返回的结果,并打印出来
				DataInputStream dis = new DataInputStream(
						socket.getInputStream());
				String line;
				StringBuffer sb = new StringBuffer();
				while ((line = dis.readLine()) != null) {
					sb.append(new String(line.getBytes("iso8859-1"), "utf8"));
				}
				for (String u : process(sb.toString())) {
					fw.write(u + "\r");
				}
				fw.flush();
				wr.close();
				socket.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			fw.close();
		}

	}

	public Set<String> process(String html) {
		Set<String> set = new HashSet<String>();
		String regex = "<a href=\"/vod/(.*?)\"(.*?)target=\"_blank\">";
		Pattern pt = Pattern.compile(regex);
		Matcher mt = pt.matcher(html);
		while (mt.find()) {
			String u = mt.group(1);
			u += mt.group(2);
			//System.out.println(u);
			set.add(u);
		}
		return set;
	}

	public String qvodProcess(String html) {
		String qvod = "";
		String regex = "qvod://(.*?)</a>";
		Pattern pt = Pattern.compile(regex);
		Matcher mt = pt.matcher(html);
		while (mt.find()) {
			qvod = mt.group(1);
		}
		return "qvod://" + qvod;
	}

	public String titleProcess(String html) {
		String title = "";
		String regex = "<h1>(.*?)</h1>";
		Pattern pt = Pattern.compile(regex);
		Matcher mt = pt.matcher(html);
		while (mt.find()) {
			title = mt.group(1);
		}
		return title;
	}

	public String categoryProcess(String html) {
		String category = "";
		String regex = "<p>所属分类：(.*?)</p>";
		Pattern pt = Pattern.compile(regex);
		Matcher mt = pt.matcher(html);
		while (mt.find()) {
			category = mt.group(1);
		}
		return category;
	}
	
	@Test
	public void testCategoryCrawler() throws IOException{
		CategoryCrawler c = new CategoryCrawler();
		c.crawl();
	}
	
	@Test
	public void testDownLoader() throws IOException{
		System.out.println(WebPageDownloader.down("www..com", "/vodlist/2_1.html"));
	}
	
	@Test
	public void testDAO() throws IOException{
		MovieDAO d = new MovieDAO();
		BufferedReader br = new BufferedReader(new FileReader(new File("D://Test//.data")));
		String line;
		while((line = br.readLine())!=null){
			String[] ss = line.split(" ");
			String regex = "\"(.*?)\"";
			Pattern pt = Pattern.compile(regex);
			Matcher mt = pt.matcher(line);
			if (mt.find()) {
				System.out.println(new Movie("www..com/vod/"+ss[0],mt.group(1),"",""));
				d.save(new Movie("www.yeyelu.com/vod/"+ss[0],mt.group(1),"",""));//http://www.yeyelu.com/vod/29504.html
			}
		}
		br.close();
		d.close();
	}

	
	
}
