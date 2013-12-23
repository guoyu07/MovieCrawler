package com.gs.socket.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
	public void post(String s) throws IOException {
		Socket socket = null;
		try {
			socket = new Socket("localhost", 8888);
			// 获取输出流，用于客户端向服务器端发送数据
			DataOutputStream dos = new DataOutputStream(
					socket.getOutputStream());
			// 获取输入流，用于接收服务器端发送来的数据
			DataInputStream dis = new DataInputStream(socket.getInputStream());
			// 客户端向服务器端发送数据
			dos.writeUTF(s);
			// 打印出从服务器端接收到的数据
			System.out.println(dis.readUTF());
			// 不需要继续使用此连接时，记得关闭哦
			socket.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
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
		String serial = sc.next();
		sc.close();
		return serial;
	}
}
