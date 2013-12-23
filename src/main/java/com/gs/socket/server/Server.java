package com.gs.socket.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Server {
	public void start() {
		ServerSocket ss = null;
		try {
		ss = new ServerSocket(8888);
		while (true) {
			//服务器接收到客户端的数据后，创建与此客户端对话的Socket
			Socket socket = ss.accept();
			//用于向客户端发送数据的输出流
			DataOutputStream dos = new DataOutputStream(
					socket.getOutputStream());
			//用于接收客户端发来的数据的输入流
			DataInputStream dis = new DataInputStream(socket.getInputStream());
			System.out.println("服务器接收到客户端的连接请求：" + dis.readUTF());
			//服务器向客户端发送连接成功确认信息
			dos.writeUTF("接受连接请求，连接成功!");
			//不需要继续使用此连接时，关闭连接
			socket.close();
			//ss.close();
		}
		} catch (IOException e) {
		e.printStackTrace();
		}
	}
}
