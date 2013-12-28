package com.gs.crawler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;

public class WebPageDownloader {
	private static final int PORT = 80;
	protected static final String down(final String strServer,final String strPage) throws IOException{
		// 用域名反向获得IP地址
		InetAddress addr = InetAddress.getByName(strServer);
		// 建立一个Socket
		Socket socket = null;
		try {
			socket = new Socket(addr, PORT);
		} catch (Exception e) {
			e.printStackTrace();
		}
		BufferedWriter wr = null;
		// 发送命令,无非就是在Socket发送流的基础上加多一些握手信息，详情请了解HTTP协议
		wr = new BufferedWriter(new OutputStreamWriter(
				socket.getOutputStream(), "utf8"));
		wr.write("GET " + strPage + " HTTP/1.0\r\n");
		wr.write("HOST:" + strServer + "\r\n");
		wr.write("Accept:*/*\r\n");
		wr.write("\r\n");
		wr.flush();
		// 接收Socket返回的结果,并打印出来
		StringBuffer sb = new StringBuffer();
		String line = "";
		BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(),"iso8859-1"));
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}
		br.close();
		wr.close();
		socket.close();
		return new String(sb.toString().getBytes("iso8859_1"),"gb2312");
	}
}
