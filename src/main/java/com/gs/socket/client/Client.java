package com.gs.socket.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.gs.socket.Request;
import com.gs.socket.Response;

public class Client {
	private Logger logger = Logger.getLogger(this.getClass());
	public void post(String query,String username) throws IOException {
		Socket socket = null;
		try {
			socket = new Socket("localhost", 8888);
			// 获取输出流，用于客户端向服务器端发送数据
			DataOutputStream dos = new DataOutputStream(
					socket.getOutputStream());
			// 获取输入流，用于接收服务器端发送来的数据
			DataInputStream dis = new DataInputStream(socket.getInputStream());
			// 客户端向服务器端发送数据
			dos.writeUTF(new Gson().toJson(new Request(getCPUID(), username, query)));
			// 打印出从服务器端接收到的数据
			Response resp = new Gson().fromJson(dis.readUTF(), Response.class);
			System.out.println(resp.getStatusCode()+resp.getJson());
			// 不需要继续使用此连接时，记得关闭哦
			socket.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			throw e;
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public String getCPUID(){
		Process process;
		Scanner sc = null;
		try {
			process = Runtime.getRuntime().exec(
					new String[] { "wmic", "cpu", "get", "ProcessorId" });
			process.getOutputStream().close();
			sc = new Scanner(process.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		sc.next();
		String serial = sc.next();
		sc.close();
		return serial;
	}
}
