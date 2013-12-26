package com.gs.socket.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.gs.socket.Request;
import com.gs.socket.RequestProcesser;
import com.gs.socket.Response;

public class Server {
	private Logger logger = Logger.getLogger(this.getClass());
	public void start() throws JsonSyntaxException,IOException,Exception {
		ServerSocket ss = null;
		Socket socket = null;
		try {
			ss = new ServerSocket(8888);
			while (true) {
				// 服务器接收到客户端的数据后，创建与此客户端对话的Socket
				socket = ss.accept();
				// 用于向客户端发送数据的输出流
				DataOutputStream dos = new DataOutputStream(
						socket.getOutputStream());
				// 用于接收客户端发来的数据的输入流
				DataInputStream dis = new DataInputStream(
						socket.getInputStream());
				String json = dis.readUTF();//接收客户端传过来的Json格式的Request
				Request req = null;
				try {
					req = new Gson().fromJson(json, Request.class);//从Json格式转换成一个response实例
					System.out.println(RequestProcesser.getQueryString(req));//TODO:处理QueryString
				} catch (Exception e) {
					logger.error(e.getMessage()+" 用户名:"+req.getUsername()+" IP:"+socket.getInetAddress().getHostAddress()+" Port:"+socket.getPort());
				}
				// 服务器向客户端发送连接成功确认信息
				dos.writeUTF(new Gson().toJson(new Response("RESULT",200)));
				// 不需要继续使用此连接时，关闭连接
				socket.close();
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (JsonSyntaxException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw e;
		}finally{
			socket.close();
			ss.close();
		}
	}
}
