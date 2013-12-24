package com.gs.crawler;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
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

public class TestDownloader {

	@Test
	public void test1() throws UnknownHostException, IOException {
		String strServer = "www.611zy.com";
		FileWriter fw = null;
		// 起始页面，/为根页
		try {
			File file = new File("D://Test//omei.data");
			fw = new FileWriter(file,true);
			for (int i = 2; i <= 78; i++) {
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
				String strPage = "/list/index2_" + String.valueOf(i) + ".html";
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
					sb.append(new String(line.getBytes("iso8859-1"), "gb2312"));
				}
				for (String u : process(sb.toString())) {
					fw.write(u+"\r");
				}
				fw.flush();
				wr.close();
				socket.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			fw.close();
		}
		
	}

	public Set<String> process(String html) {
		Set<String> set = new HashSet<String>();
		String regex = "<a href=\"/view/(.*?)\" title=\".*?\" target=\"_blank\">";
		Pattern pt = Pattern.compile(regex);
		Matcher mt = pt.matcher(html);
		while (mt.find()) {
			String u = mt.group(1);
			set.add(u);
		}
		return set;
	}
	
	@Test
	public void test2() throws UnknownHostException, IOException {
		String strServer = "www.611zy.com";
		FileWriter fw = null;
		// 起始页面，/为根页
		try {
			File file = new File("D://Test//omei.data");
			fw = new FileWriter(file,true);
			for (int i = 2; i <= 78; i++) {
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
				String strPage = "/list/index2_" + String.valueOf(i) + ".html";
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
					sb.append(new String(line.getBytes("iso8859-1"), "gb2312"));
				}
				for (String u : process(sb.toString())) {
					fw.write(u+"\r");
				}
				fw.flush();
				wr.close();
				socket.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			fw.close();
		}
		
	}
}
